package com.research.videoAnalyze.controllers;

import com.research.videoAnalyze.models.ProcessDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ScheduledTasks {

    @Value("${python.backend.url}")
    String backendurl;

    @Autowired
    VideoRepository videoRepository;

    List<ProcessDAO> list;
    Processes procObj;

    private static final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 120000)
    public void scheduleTaskWithFixedRate() {
        System.out.println("Scheduled Task started:: Execution Time - { " + dateTimeFormatter.format(new Date()) + " }");

        //send processing completed email to users
/*        list = videoRepository.get50CompletedProcessesList();
        procObj = new Processes();

        for(ProcessDAO dao : list){
            String userId = dao.getUserId();

            //get user email from database
            String receiverEmail = "";

            //procObj.sendEmail(receiverEmail , );
        }
*/

        //send request to python backend check unprocessed videos
/*       String uri = backendurl;
        String result;

        Map<String, String> map = new HashMap<>();
        map.put("start","start");

        RestTemplate restTemplate = new RestTemplate();
        result = restTemplate.postForObject(uri, map, String.class);
        System.out.println(result);*/
    }
}
