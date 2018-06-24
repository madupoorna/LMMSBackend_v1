package com.research.videoAnalyze.models;

import com.google.api.services.youtube.YouTube;

import java.util.List;

public class FilterModel {

    private List<String> duration;
    private List<String> IDE;
    private String quality;
    private String codeVisibility;
    private String presenterVisibility;

    public List<String> getDuration() {
        return duration;
    }

    public void setDuration(List<String> duration) {
        this.duration = duration;
    }

    public List<String> getIDE() {
        return IDE;
    }

    public void setIDE(List<String> IDE) {
        this.IDE = IDE;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getCodeVisibility() {
        return codeVisibility;
    }

    public void setCodeVisibility(String codeVisibility) {
        this.codeVisibility = codeVisibility;
    }

    public String getPresenterVisibility() {
        return presenterVisibility;
    }

    public void setPresenterVisibility(String presenterVisibility) {
        this.presenterVisibility = presenterVisibility;
    }
}
