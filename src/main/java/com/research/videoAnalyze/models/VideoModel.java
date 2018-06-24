package com.research.videoAnalyze.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "videos")
public class VideoModel {

    @Id
    public String id;

    @Indexed(unique = true)
    private String videoUrl;

    private String title;
    private String thumbnailUrl;
    private String duration;
    private String description;

    @TextIndexed
    private String searchKeywords;

    private String filter1;//quality(resolution HD)
    private String filter2;//presenter visible in  the video
    private String filter3;//code visible in the video
    private String filter4;//IDE : Eclipse
    private String filter5;//IDE : Intellij (Idea, Pycharm, webstorm)
    private String filter6;//IDE : Visual studio
    private String filter7;//IDE : Net beans
    private String filter8;//IDE : Visual studio Code
    private String filter9;//IDE : Android studio

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSearchKeywords() {
        return searchKeywords;
    }

    public void setSearchKeywords(String searchKeywords) {
        this.searchKeywords = searchKeywords;
    }

    public String getFilter1() {
        return filter1;
    }

    public void setFilter1(String filter1) {
        this.filter1 = filter1;
    }

    public String getFilter2() {
        return filter2;
    }

    public void setFilter2(String filter2) {
        this.filter2 = filter2;
    }

    public String getFilter3() {
        return filter3;
    }

    public void setFilter3(String filter3) {
        this.filter3 = filter3;
    }

    public String getFilter4() {
        return filter4;
    }

    public void setFilter4(String filter4) {
        this.filter4 = filter4;
    }

    public String getFilter5() {
        return filter5;
    }

    public void setFilter5(String filter5) {
        this.filter5 = filter5;
    }

    public String getFilter6() {
        return filter6;
    }

    public void setFilter6(String filter6) {
        this.filter6 = filter6;
    }

    public String getFilter7() {
        return filter7;
    }

    public void setFilter7(String filter7) {
        this.filter7 = filter7;
    }

    public String getFilter8() {
        return filter8;
    }

    public void setFilter8(String filter8) {
        this.filter8 = filter8;
    }

    public String getFilter9() {
        return filter9;
    }

    public void setFilter9(String filter9) {
        this.filter9 = filter9;
    }

    @Override
    public String toString() {
        return String.format(
                        "Videos[id=%s, " +
                        "title='%s', " +
                        "url='%s', " +
                        "thumbnailUrl='%s', " +
                        "duration='%s', " +
                        "description='%s', " +
                        "filter1='%s', " +
                        "filter2='%s', " +
                        "filter3='%s', " +
                        "filter4='%s', " +
                        "filter5='%s', " +
                        "filter6='%s', " +
                        "filter7='%s', " +
                        "filter8='%s', " +
                        "filter9='%s']",
                id,
                title,
                videoUrl,
                thumbnailUrl,
                duration,
                description,
                filter1,
                filter2,
                filter3,
                filter4,
                filter5,
                filter6,
                filter7,
                filter8,
                filter9);
    }
}
