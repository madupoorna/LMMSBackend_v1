package com.research.videoAnalyze.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
public class LinksModel {

    @Id
    @GeneratedValue
    private Long id;

    private String thumbnail;
    private String url;
    private String duration;
    private String Title;
    private String description;
    private String emptyList;

    public String getEmptyList() {
        return emptyList;
    }

    public void setEmptyList(String emptyList) {
        this.emptyList = emptyList;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
