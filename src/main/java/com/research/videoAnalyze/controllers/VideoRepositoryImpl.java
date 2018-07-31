package com.research.videoAnalyze.controllers;

import com.google.api.services.youtube.model.Video;
import com.research.videoAnalyze.models.FilterModel;
import com.research.videoAnalyze.models.ProcessDAO;
import com.research.videoAnalyze.models.VideoModel;
import com.research.videoAnalyze.models.VideoURLDAO;
import org.bson.BsonDocument;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VideoRepositoryImpl implements VideoRepository {

    private MongoTemplate mongoTemplate;

    @Autowired
    public VideoRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Get list of all {@link VideoModel} objects.
     */
    @Override
    public List<VideoModel> getAllVideos() {
        return mongoTemplate.findAll(VideoModel.class);
    }

    public void updateProcessFlag(String flag, String processId) {

        ProcessDAO model = mongoTemplate.findOne(Query.query(Criteria.where("processId").is(processId)), ProcessDAO.class);
        model.setProcessFlag(flag);

        mongoTemplate.save(model, "video_processes");
    }

    public void insertProcess(String userId, String keyword, FilterModel filtDAO, List<String> list) {

        ProcessDAO procObj = new ProcessDAO();
        procObj.setUserId(userId);
        procObj.setKeywords(keyword);
        procObj.setFilters(filtDAO);
        procObj.setLinksList(list);
        procObj.setProcessFlag("1");

        mongoTemplate.insert(procObj);
    }

    /**
     * Get list of all links {@link VideoURLDAO} objects.
     */
    @Override
    public List<VideoURLDAO> getLLinks() {

        Query query = new Query();
        query.fields().include("videoUrl");

        return mongoTemplate.find(query, VideoURLDAO.class);
    }

    /**
     * Get list of {@link VideoModel} objects that match search keyword.
     */
    @Override
    public List<VideoModel> findBySearchKeywords(String keyWord, FilterModel filtModel) {

        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(keyWord);
        Query query = TextQuery.queryText(criteria).sortByScore();

        Criteria criter = new Criteria();

        if (!filtModel.getEmptyFilt()) {
            if (filtModel.getFilter1() != false) {
                criter = criter.where("filter1").is(filtModel.getFilter1());
            }
            if (filtModel.getFilter2() != false) {
                criter = criter.where("filter2").is(filtModel.getFilter2());
            }
            if (filtModel.getFilter3() != false) {
                criter = criter.where("filter3").is(filtModel.getFilter3());
            }
            if (filtModel.getFilter4() != false) {
                criter = criter.where("filter4").is(filtModel.getFilter4());
            }
            if (filtModel.getFilter5() != false) {
                criter = criter.where("filter5").is(filtModel.getFilter5());
            }
            if (filtModel.getFilter6() != false) {
                criter = criter.where("filter6").is(filtModel.getFilter6());
            }
            if (filtModel.getFilter7() != false) {
                criter = criter.where("filter7").is(filtModel.getFilter7());
            }
            if (filtModel.getFilter8() != false) {
                criter = criter.where("filter8").is(filtModel.getFilter8());
            }
            if (filtModel.getFilter9() != false) {
                criter = criter.where("filter9").is(filtModel.getFilter9());
            }
            if (filtModel.getDuration1() != false) {
                criter = criter.where("duration1").is(filtModel.getDuration1());
            }
            if (filtModel.getDuration2() != false) {
                criter = criter.where("duration2").is(filtModel.getDuration2());
            }
            if (filtModel.getDuration3() != false) {
                criter = criter.where("duration3").is(filtModel.getDuration3());
            }

            query.addCriteria(new Criteria().andOperator(criter));

        }

        TextIndexDefinition textIndex = new TextIndexDefinition.TextIndexDefinitionBuilder()
                .onField("title")
                .onField("description")
                .onField("searchKeywords")
                .build();

        mongoTemplate.indexOps(VideoModel.class).ensureIndex(textIndex);

        List<VideoModel> model1 = mongoTemplate.find(query, VideoModel.class);
        return model1;

    }

    /**
     * Saves a {@link VideoModel}.
     */
    @Override
    public void saveVideo(VideoModel object) {

    }

    /**
     * Create a {@link VideoModel} collection if the collection does not already
     * exists
     */
    @Override
    public void createCollection() {

        VideoModel model = new VideoModel();

        model.setTitle("Multithreading in Java Practical");
        model.setVideoUrl("https://www.youtube.com/watch?v=Xj1uYKa8rIw");
        model.setThumbnailUrl("https://img.youtube.com/vi/Xj1uYKa8rIw/default.jpg");
        model.setDuration("12:30");
        model.setDescription("In computer science, a thread of execution is the smallest sequence of programmed instructions that can be managed independently by a scheduler, which is typically a part of the operating system. Multithreading in java is a process of executing multiple activities can proceed concurrently in the same program. Thread is basically a lightweight sub-process, a smallest unit of processing. In multithreading threads share a common memory area. They don't allocate separate memory area so saves memory, and context-switching between the threads takes less time than process. Multiple threads can exist within the same process and share resources such as memory, while different processes do not share these resources. Using two different task at the same time means multi-tasking. Thread is unit of a process. visit our website : www.telusko.com facebook page : https://goo.gl/kNnJvG google plus : https://goo.gl/43Fa7i Subscribe to the channel and learn Programming in easy way. Java Tutorial for Beginners : https://goo.gl/p10QfB Scala Tutorials for Java Developers : https://goo.gl/8H1aE5 C Tutorial Playlist : https://goo.gl/8v92pu Android Tutorial for Beginners Playlist : https://goo.gl/MzlIUJ XML Tutorial : https://goo.gl/Eo79do Design Patterns in Java : https://goo.gl/Kd2MWE Socket Programming in Java : https://goo.gl/jlMEbg Spring MVC Tutorial : https://goo.gl/9ubbG2 OpenShift Tutorial for Beginners : https://goo.gl/s58BQH Spring Framework with Maven : https://goo.gl/MaEluO Sql Tutorial for Beginners : https://goo.gl/x3PrTg String Handling in Java : https://goo.gl/zUdPwa Array in Java : https://goo.gl/uXTaUy Java Servlet : https://goo.gl/R5nHp8 Exception Handling in Java : https://goo.gl/N4NbAW");
        model.setSearchKeywords("Multithreading in Java Practical, threading, Multi Threading, java");
        model.setDuration1(false);
        model.setDuration2(true);
        model.setDuration3(false);
        model.setFilter1(true);
        model.setFilter2(true);
        model.setFilter3(true);
        model.setFilter4(true);
        model.setFilter5(false);
        model.setFilter6(false);
        model.setFilter7(false);
        model.setFilter8(false);
        model.setFilter9(false);

        mongoTemplate.insert(model);
    }

    /**
     * Drops the {@link VideoModel} collection if the collection does already exists
     */
    @Override
    public void dropCollection() {
        mongoTemplate.dropCollection("videos");
        //mongoTemplate.dropCollection("video_processes");
    }

    @Override
    public void updateDetailsInVideo(String videoUrl, Update update) {

        Query query = new Query();
        query.addCriteria(Criteria.where("videoUrl").is(videoUrl.trim()));
        System.out.println(query);
//        VideoModel video = mongoTemplate.findOne(query, VideoModel.class);
//        System.out.println("video d " + video);
//
//        if(video != null) {
//            video.setTitle(model.getTitle());
//            video.setThumbnailUrl(model.getThumbnailUrl());
//            video.setDuration(model.getDuration());
//            video.setDescription(model.getDescription());
//            video.setSearchKeywords(model.getSearchKeywords());
//            video.setFilter1(model.getFilter1());
//            video.setFilter1(model.getDuration1());
//            video.setDuration2(model.getDuration2());
//            video.setDuration3(model.getDuration3());
//
//
//            mongoTemplate.insert(video, "videos");
//        }
        mongoTemplate.updateFirst(query, update, VideoModel.class);

    }

    public List<ProcessDAO> get50CompletedProcessesList() {

        return mongoTemplate.find(Query.query(Criteria.where("processFlag").is("3")).limit(50), ProcessDAO.class);

    }
}