package com.research.videoAnalyze.processes;

import com.research.videoAnalyze.models.VideoModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VideoRepository{

    List<VideoModel> getAllVideos();

    List<VideoModel> findBySearchKeywords(String keyword);

    void saveVideo(VideoModel object);

    void createCollection();

    void dropCollection();

}
