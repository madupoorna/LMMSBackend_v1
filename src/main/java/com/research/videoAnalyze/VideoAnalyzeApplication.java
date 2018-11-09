package com.research.videoAnalyze;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.research.videoAnalyze.controllers")
public class VideoAnalyzeApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoAnalyzeApplication.class, args);
    }

}
