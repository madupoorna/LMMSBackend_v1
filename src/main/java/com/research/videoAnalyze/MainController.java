package com.research.videoAnalyze;

import com.google.gson.Gson;
import com.research.videoAnalyze.controllers.EmailService;
import com.research.videoAnalyze.controllers.Processes;
import com.research.videoAnalyze.controllers.VideoRepository;
import com.research.videoAnalyze.controllers.YouTubeCrawler;
import com.research.videoAnalyze.models.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MainController {

    @Autowired
    VideoRepository videoRepository;

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

        for (String url : urls){
            System.out.println(url);
        }

        YouTubeCrawler proobj = new YouTubeCrawler();
//        Processes emailObj = new Processes();
        String id;

        for (String url : urls) {
            id = url.trim().replace("https://www.youtube.com/watch?v=", "");
            ReturnModel obj = proobj.getDetailsById(id);
            videoRepository.updateDetailsInVideo(obj.getVideourl(), obj.getUpdate());
        }

        System.out.println("finished");
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
