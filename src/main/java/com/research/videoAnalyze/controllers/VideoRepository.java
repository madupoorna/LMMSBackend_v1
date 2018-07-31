package com.research.videoAnalyze.controllers;

import com.google.api.services.youtube.model.Video;
import com.research.videoAnalyze.models.FilterModel;
import com.research.videoAnalyze.models.ProcessDAO;
import com.research.videoAnalyze.models.VideoURLDAO;
import com.research.videoAnalyze.models.VideoModel;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public interface VideoRepository {

    List<VideoModel> getAllVideos();

    List<VideoModel> findBySearchKeywords(String key, FilterModel filtModel);

    List<VideoURLDAO> getLLinks();

    void updateProcessFlag(String flag, String processId);

    void saveVideo(VideoModel object);

    void createCollection();

    void dropCollection();

    void insertProcess(String userId, String keyword, FilterModel filtDAO, List<String> list);

    void updateDetailsInVideo(String videoUrl, Update update);

    List<ProcessDAO> get50CompletedProcessesList();

}
