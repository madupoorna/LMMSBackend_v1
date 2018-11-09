package com.research.videoAnalyze.models;

import java.util.List;

public class JsonModel {

    private List<String> urlList;
    private String process_id;

    public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }

    public String getProcess_id() {
        return process_id;
    }

    public void setProcess_id(String process_id) {
        this.process_id = process_id;
    }
}
