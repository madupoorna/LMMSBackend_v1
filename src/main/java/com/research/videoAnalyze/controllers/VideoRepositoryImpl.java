package com.research.videoAnalyze.controllers;

import com.research.videoAnalyze.models.FilterModel;
import com.research.videoAnalyze.models.ProcessDAO;
import com.research.videoAnalyze.models.VideoURLDAO;
import com.research.videoAnalyze.models.VideoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
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
        return null;
    }


    public void updateProcessFlag(String flag, String processId) {

        ProcessDAO model = mongoTemplate.findOne(Query.query(Criteria.where("processId").is(processId)), ProcessDAO.class);
        model.setProcessFlag(flag);

        mongoTemplate.save(model, "video_processes");
    }

    public void insertProcess(ProcessDAO model) {

        mongoTemplate.save(model, "video_processes");
    }

    public ProcessDAO getIncompleteProcesses(){

        ProcessDAO model = mongoTemplate.findOne(Query.query(Criteria.where("processFlag").is("2")), ProcessDAO.class);
        return model;

    }

    /**
     * Get list of all links {@link VideoURLDAO} objects.
     */
    @Override
    public List<VideoURLDAO> getLLinks(){

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
            if (filtModel.getFilter1() != null) {
                criter = criter.and("filter1").is(filtModel.getFilter1());
            }
            if (filtModel.getFilter2() != null) {
                criter = criter.and("filter2").is(filtModel.getFilter2());
            }
            if (filtModel.getFilter3() != null) {
                criter = criter.and("filter3").is(filtModel.getFilter3());
            }
            if (filtModel.getFilter4() != null) {
                criter = criter.and("filter4").is(filtModel.getFilter4());
            }
            if (filtModel.getFilter5() != null) {
                criter = criter.and("filter5").is(filtModel.getFilter5());
            }
            if (filtModel.getFilter6() != null) {
                criter = criter.and("filter6").is(filtModel.getFilter6());
            }
            if (filtModel.getFilter7() != null) {
                criter = criter.and("filter7").is(filtModel.getFilter7());
            }
            if (filtModel.getFilter8() != null) {
                criter = criter.and("filter8").is(filtModel.getFilter8());
            }
            if (filtModel.getFilter9() != null) {
                criter = criter.and("filter9").is(filtModel.getFilter9());
            }
            if (filtModel.getDuration1() != null) {
                criter = criter.and("duration1").is(filtModel.getDuration1());
            }
            if (filtModel.getDuration2() != null) {
                criter = criter.and("duration2").is(filtModel.getDuration2());
            }
            if (filtModel.getDuration3() != null) {
                criter = criter.and("duration3").is(filtModel.getDuration3());
            }

            query.addCriteria(new Criteria().orOperator(criter));
            System.out.println(query);

        }
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

    }

    /**
     * Drops the {@link VideoModel} collection if the collection does already exists
     */
    @Override
    public void dropCollection() {

    }
}