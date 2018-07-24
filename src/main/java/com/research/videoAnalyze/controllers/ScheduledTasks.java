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

@Component
public class ScheduledTasks {

    @Value("${python.backend.url}")
    String backendurl;

    @Autowired
    VideoRepository videoRepository;

    private static final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 120000)
    public void scheduleTaskWithFixedRate() throws UnsupportedEncodingException, MalformedURLException, URISyntaxException {
        System.out.println("Fixed Rate Task :: Execution Time - {}" + dateTimeFormatter.format(new Date()));

        ProcessDAO procObj = videoRepository.getIncompleteProcesses();

        if (procObj != null) {

            String uri = backendurl;
            String result;

            RestTemplate restTemplate = new RestTemplate();
            result = restTemplate.postForObject(uri, procObj, String.class);

            System.out.println("backend post result " + result);
        }
    }
}
