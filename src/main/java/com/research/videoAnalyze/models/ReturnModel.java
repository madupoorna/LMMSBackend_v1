package com.research.videoAnalyze.models;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class ReturnModel {

    private String videourl;
    private Update update;

    public void setUpdate(Update update) {
        this.update = update;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    public Update getUpdate() {
        return update;
    }
}
