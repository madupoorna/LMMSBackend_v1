package com.research.videoAnalyze.controllers;

import com.google.api.services.youtube.model.Video;
import com.research.videoAnalyze.models.*;
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

    ProcessDAO getProcessDetailsById(String processId);

    UserModel getUserDetailsById(String userId);

    List<ProcessDAO> get50CompletedProcessesList();

}
