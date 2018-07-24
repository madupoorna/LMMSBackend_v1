package com.research.videoAnalyze;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VideoAnalyzeApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoAnalyzeApplication.class, args);
    }

}
