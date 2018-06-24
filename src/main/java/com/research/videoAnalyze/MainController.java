package com.research.videoAnalyze;

import com.research.videoAnalyze.models.LinksModel;
import com.research.videoAnalyze.models.SearchModel;
import com.research.videoAnalyze.models.VideoModel;
import com.research.videoAnalyze.processes.Processes;
import com.research.videoAnalyze.processes.VideoRepository;
import com.research.videoAnalyze.processes.YouTubeCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MainController {

    @Autowired
    VideoRepository videoRepository;

    HashMap<String, String> parameters;

    /**
     *
     * @param model
     * Identify related videos from db
     * @return list of videos
     *
     */
    @PostMapping("/api/index/evideos")
    @ResponseBody
    public List<VideoModel> getVideosFromDB(@RequestBody SearchModel model) {

        videoRepository.createCollection();
        List<VideoModel> videos = videoRepository.findBySearchKeywords(model.getSearchKeyword());
        System.out.println("getVideosFromDB");
        return videos;
    }

    /**
     *
     * @param model
     * identify nd process new videos
     * @return
     */
    @PostMapping("/api/index/tvideos")
    @ResponseBody
    public Map<String, String> newSearch(@RequestBody SearchModel model) {

        HashMap<String, String> map = new HashMap<>();
        map.put("response", "processing");

        //place to start video identify process

        return map;
    }

    @RequestMapping("/api/index/create")
    public void save() {

        videoRepository.createCollection();

        VideoModel model = new VideoModel();
        model.setTitle("Java Programming: Let's Build a Game #1");
        model.setVideoUrl("https://www.youtube.com/watch?v=1gir2R7G9ws1");
        model.setDuration("20:29");
        model.setDescription("Make Video Games 2018 ▻ https://www.codingmadesimple.com/courses/ Part 1 of a series on the very basic fundamentals of Java game design. If you have ...");
        model.setThumbnailUrl("https://i.ytimg.com/vi/1gir2R7G9ws/default.jpg1");
        model.setSearchKeywords("java java programming, build game with java");
        model.setFilter1("HD");
        model.setFilter2("false");
        model.setFilter3("true");
        model.setFilter4("true");
        model.setFilter5("false");
        model.setFilter6("true");
        model.setFilter7("fasle");
        model.setFilter8("false");
        model.setFilter9("false");

        videoRepository.saveVideo(model);
    }

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

        return new YouTubeCrawler().search(parameters);
    }
}
