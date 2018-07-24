package com.research.videoAnalyze.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "videos")
public class VideoModel {

    @Id
    public String id;

    @Indexed(unique = true)
    private String videoUrl;

    private String title;
    private String thumbnailUrl;
    private String duration1;// >20
    private String duration2;// <4
    private String duration3;// 4-20
    private String description;
    private String searchKeywords;

    private String filter1;// quality(resolution HD)
    private String filter2;// presenter visible in  the video
    private String filter3;// code visible in the video
    private boolean filter4;// IDE : Eclipse
    private boolean filter5;// IDE : Intellij (Idea, Pycharm, webstorm)
    private boolean filter6;// IDE : Visual studio
    private boolean filter7;// IDE : Net beans
    private boolean filter8;// IDE : Visual studio Code
    private boolean filter9;// IDE : Android studio

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

    public String getDuration1() {
        return duration1;
    }

    public void setDuration1(String duration1) {
        this.duration1 = duration1;
    }

    public String getDuration2() {
        return duration2;
    }

    public void setDuration2(String duration2) {
        this.duration2 = duration2;
    }

    public String getDuration3() {
        return duration3;
    }

    public void setDuration3(String duration3) {
        this.duration3 = duration3;
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

    public boolean getFilter4() {
        return filter4;
    }

    public void setFilter4(boolean filter4) {
        this.filter4 = filter4;
    }

    public boolean getFilter5() {
        return filter5;
    }

    public void setFilter5(boolean filter5) {
        this.filter5 = filter5;
    }

    public boolean getFilter6() {
        return filter6;
    }

    public void setFilter6(boolean filter6) {
        this.filter6 = filter6;
    }

    public boolean getFilter7() {
        return filter7;
    }

    public void setFilter7(boolean filter7) {
        this.filter7 = filter7;
    }

    public boolean getFilter8() {
        return filter8;
    }

    public void setFilter8(boolean filter8) {
        this.filter8 = filter8;
    }

    public boolean getFilter9() {
        return filter9;
    }

    public void setFilter9(boolean filter9) {
        this.filter9 = filter9;
    }

    @Override
    public String toString() {
        return String.format(
                "Videos[id=%s, " +
                        "title='%s', " +
                        "url='%s', " +
                        "thumbnailUrl='%s', " +
                        "duration1='%s', " +
                        "duration2='%s', " +
                        "duration3='%s', " +
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
                duration1,
                duration2,
                duration3,
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