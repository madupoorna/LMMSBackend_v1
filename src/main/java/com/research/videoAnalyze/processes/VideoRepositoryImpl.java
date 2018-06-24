package com.research.videoAnalyze.processes;

import com.mongodb.*;
import com.research.videoAnalyze.models.VideoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VideoRepositoryImpl implements VideoRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * Get list of all {@link VideoModel} objects.
     */
    @Override
    public List<VideoModel> getAllVideos() {
        return null;
    }

    /**
     * Get list of {@link VideoModel} objects that match search keyword.
     */
    @Override
    public List<VideoModel> findBySearchKeywords(String keyword) {
        Query query = new Query();
        query.limit(10);
        query.addCriteria(Criteria.where("searchKeywords").regex(keyword));

        return mongoTemplate.find(query, VideoModel.class);
    }

    /**
     * Saves a {@link VideoModel}.
     */
    @Override
    public void saveVideo(VideoModel object) {
        mongoTemplate.insert(object);
    }

    /**
     * Create a {@link VideoModel} collection if the collection does not already
     * exists
     */
    @Override
    public void createCollection() {
        if (!mongoTemplate.collectionExists(VideoModel.class)) {
            mongoTemplate.createCollection(VideoModel.class);
        }
    }

    /**
     * Drops the {@link VideoModel} collection if the collection does already exists
     */
    @Override
    public void dropCollection() {
        if (mongoTemplate.collectionExists(VideoModel.class)) {
            mongoTemplate.dropCollection(VideoModel.class);
        }
    }
}
