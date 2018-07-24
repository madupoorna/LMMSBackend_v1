package com.research.videoAnalyze.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "video_processes")
public class ProcessDAO {

    @Id
    public String processId;
    private String userId;
    private String keywords;
    private FilterModel filters;
    private String processFlag; //0 = processing completed, 1 = sent for processing, 2 = not processed
    private List<String> linksList;

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public FilterModel getFilters() {
        return filters;
    }

    public void setFilters(FilterModel filters) {
        this.filters = filters;
    }

    public String getProcessFlag() {
        return processFlag;
    }

    public void setProcessFlag(String processFlag) {
        this.processFlag = processFlag;
    }

    public List<String> getLinksList() {
        return linksList;
    }

    public void setLinksList(List<String> linksList) {
        this.linksList = linksList;
    }
}
