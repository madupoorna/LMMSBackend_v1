package com.research.videoAnalyze.models;

import java.util.List;

public class OntologyModel {

    List<String> languages;
    List<String> stopWords;

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public List<String> getStopWords() {
        return stopWords;
    }

    public void setStopWords(List<String> stopWords) {
        this.stopWords = stopWords;
    }
}
