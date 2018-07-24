package com.research.videoAnalyze;

import com.research.videoAnalyze.controllers.Processes;
import com.research.videoAnalyze.controllers.VideoRepository;
import com.research.videoAnalyze.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MainController {

    @Autowired
    VideoRepository videoRepository;

    /**
     * @param model Identify related videos from db
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
     *
     * @param model
     * identify and process new videos
     * @return
     */
    @PostMapping("/api/index/nvideos")
    @ResponseBody
    public ResponseEntity newSearch(@RequestBody SearchModel model) {

        System.out.println("processing new search for keyword "+ model.getSearchKeyword());

        Processes proobj = new Processes();

        String keyword = model.getSearchKeyword();
        String userId = model.getUserId();

        FilterModel filtDAO = proobj.separateFilters(model.getFilters());

        List<VideoURLDAO> videoLinksList = videoRepository.getLLinks();
       // System.out.println("videoLinksList"+ videoLinksList.get(0));

        List<String> list = proobj.getYouTubeVideoList(filtDAO, keyword, videoLinksList, "5");

        ProcessDAO procObj = new ProcessDAO();
        procObj.setUserId(userId);
        procObj.setKeywords(keyword);
        procObj.setFilters(filtDAO);
        procObj.setLinksList(list);
        procObj.setProcessFlag("2");

        videoRepository.insertProcess(procObj);//add process
        System.out.println("added process to database");

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/api/index/test")
    @ResponseBody
    public ResponseEntity test(@RequestBody ProcessDAO model) {

        System.out.println("post received for processing python backend");
        System.out.println(model.getKeywords());
        System.out.println(model.getFilters());

        return new ResponseEntity(HttpStatus.OK);
    }

/*
    @RequestMapping("/api/index/create")
    public void save() {

        videoRepository.createCollection();

        VideoModel model = new VideoModel();
        model.setTitle("C# Fundamentals for Absolute Beginners");
        model.setVideoUrl("https://www.youtube.com/watch?v=nRjHGKaJY8M");
        model.setDuration("8:17:56");
        model.setDescription("C# Basics for Beginners: Learn C# Fundamentals by Coding ☞ http://deal.codetrick.net/p/BkW_5OZng C# Advanced Topics: Take Your C# Skills to the Next Level");
        model.setThumbnailUrl("https://i.ytimg.com/vi/nRjHGKaJY8M/hqdefault.jpg?sqp=-oaymwEXCPYBEIoBSFryq4qpAwkIARUAAIhCGAE=&rs=AOn4CLDAFucMbxu4oYL1MjChDY_XGfGTTQ");
        model.setSearchKeywords("c# programming, c sharp progrmming , c# for beginners, c sharp for beginners, c# from scratch, c#, c sharp, basics, Fundamentals");
        model.setFilter1("720p");
        model.setFilter2("true");
        model.setFilter3("true");
        model.setFilter4("false");
        model.setFilter5("false");
        model.setFilter6("false");
        model.setFilter7("false");
        model.setFilter8("false");
        model.setFilter9("false");

        videoRepository.saveVideo(model);
    }*/

/*
    @PostMapping("/api/index/nvideos")
    @ResponseBody
    public List<LinksModel> searchInYouTube(@RequestBody SearchModel model) {

//    duration > 20 min,       long
//    duration < 4 min,        short
//    duration (4 - 20) min,   medium
//    HD quality,              HD
//    presenter visible in  the video,
//    code visible in the video,
//    IDE : Eclipse,
//    IDE : Intellij (Idea, Pycharm, webstorm),
//    IDE : Visual studio,
//    IDE : Net beans,
//    IDE : Visual studio Code,
//    IDE : Android studio

        System.out.println("processing");

        Processes proobj = new Processes();

        String keyword = model.getSearchKeyword();

        String duration;//any,long,medium,short
        if (proobj.separateFilters(model.getFilters()).get("duration") != null) {
            duration = proobj.separateFilters(model.getFilters()).get("duration");
        } else {
            duration = "any";
        }
        String quality;//any,high,standard
        if (proobj.separateFilters(model.getFilters()).get("quality") != null) {
            quality = proobj.separateFilters(model.getFilters()).get("quality");
        } else {
            quality = "any";
        }
        String noOfResults = "5";

        parameters = new HashMap<>();
        parameters.put("q", keyword);
        parameters.put("videoDuration", duration);
        parameters.put("videoDefinition", quality);
        parameters.put("maxResults", noOfResults);

        //return new YouTubeCrawler().search(parameters);
    }
*/

}
