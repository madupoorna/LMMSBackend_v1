package com.research.videoAnalyze.controllers;

import com.google.gson.Gson;
import com.research.videoAnalyze.models.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MainController {

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    JavaMailSender sender;

    @Autowired
    Configuration freemarkerConfig;

    /**
     * Identify related videos for search filters from db
     *
     * @param model
     * @return list of videos
     */
    @PostMapping("/api/index/evideos")
    @ResponseBody
    public List<VideoModel> getVideosFromDB(@RequestBody SearchModel model) {

        System.out.println("processing database search for keyword " + model.getSearchKeyword());

        List<VideoModel> videos = videoRepository.findBySearchKeywords(model.getSearchKeyword(), new Processes().separateFilters(model.getFilters()));

        return videos;
    }

    /**
     * identify and process new videos
     *
     * @param model
     * @return
     */
    @PostMapping("/api/index/nvideos")
    @ResponseBody
    public ResponseEntity newSearch(@RequestBody SearchModel model) {

        System.out.println("processing new search for keyword " + model.getSearchKeyword());

        Processes proobj = new Processes();

        String keyword = model.getSearchKeyword();
        String userId = model.getUserId();

        FilterModel filtDAO = proobj.separateFilters(model.getFilters());

        List<VideoURLDAO> videoLinksList = videoRepository.getLLinks();

        List<String> list = proobj.getYouTubeVideoList(filtDAO, keyword, videoLinksList, "5");

        for(String id : list) {
            System.out.println("links :" + id);
        }

        videoRepository.insertProcess(userId, keyword, filtDAO, list);//add process
        System.out.println("added process to database");

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * save processed new video
     * @param payload
     */
    @PostMapping("/api/index/create")
    public void save(@RequestBody String payload) throws JSONException {

        Gson gson = new Gson();

        String jsonInString = payload.replace("\\","");
        JsonModel model = gson.fromJson(jsonInString, JsonModel.class);

        System.out.println("payload : " + model);

        List<String> urls = model.getUrlList();
        String processId = model.getProcess_id();

        System.out.println("processId : "+ processId);

        for (String url : urls){
            System.out.println(url);
        }

        YouTubeCrawler proobj = new YouTubeCrawler();
        String id;

        for (String url : urls) {
            id = url.trim().replace("https://www.youtube.com/watch?v=", "");
            ReturnModel obj = proobj.getDetailsById(id);
            videoRepository.updateDetailsInVideo(obj.getVideourl(), obj.getUpdate());
        }

        ProcessDAO procObj = videoRepository.getProcessDetailsById(processId);

        UserModel userModel = videoRepository.getUserDetailsById(procObj.getUserId());

        String keyword = procObj.getKeywords();
        String userName = userModel.getUserName();
        String receiverEmail = userModel.getEmail();

        System.out.println("userId : " + procObj.getUserId());
        System.out.println("receiver email : " + receiverEmail);
        System.out.println("username : " + userName);
        System.out.println("keyword : " + keyword);

        //send email
        try {
            MimeMessage message = sender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message);

            Map<String, Object> mailModel = new HashMap();
            mailModel.put("user", userName);
            mailModel.put("searchWord", keyword);

            Template t = freemarkerConfig.getTemplate("email.ftl");
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, mailModel);

            helper.setTo(receiverEmail);
            helper.setText(text, true); // set to html
            helper.setSubject("Hi");

            sender.send(message);
            System.out.println("Email sent");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("create")
    public String createCollection(){
        videoRepository.createCollection();
        return "created ";
    }

    @GetMapping("drop")
    public String dropCollection(){
        videoRepository.dropCollection();
        return "dropped";
    }

    @GetMapping("get")
    public List<VideoModel> getCollection(){
        return  videoRepository.getAllVideos();
    }

    @GetMapping("test")
    public String test(){
        System.out.println("test");
        return "created ";
    }

}
