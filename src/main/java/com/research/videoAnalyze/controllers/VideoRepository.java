package com.research.videoAnalyze.controllers;

import com.research.videoAnalyze.models.FilterModel;
import com.research.videoAnalyze.models.ProcessDAO;
import com.research.videoAnalyze.models.VideoURLDAO;
import com.research.videoAnalyze.models.VideoModel;

import java.util.List;

public interface VideoRepository {

    List<VideoModel> getAllVideos();

    List<VideoModel> findBySearchKeywords(String key, FilterModel filtModel);

    List<VideoURLDAO> getLLinks();

    void updateProcessFlag(String flag, String processId);

    void saveVideo(VideoModel object);

    void createCollection();

    void dropCollection();

    void insertProcess(ProcessDAO model);

    ProcessDAO getIncompleteProcesses();

}
