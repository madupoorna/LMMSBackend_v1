package com.research.videoAnalyze.models;

import com.google.api.services.youtube.YouTube;

import java.util.List;

public class FilterModel {

    private String duration1;// >20
    private String duration2;// <4
    private String duration3;// 4-20
    private String filter1;// quality(resolution HD)
    private String filter2;// presenter visible in  the video
    private String filter3;// code visible in the video
    private String filter4;// IDE : Eclipse
    private String filter5;// IDE : Intellij (Idea, Pycharm, webstorm)
    private String filter6;// IDE : Visual studio
    private String filter7;// IDE : Net beans
    private String filter8;// IDE : Visual studio Code
    private String filter9;// IDE : Android studio
    private boolean emptyFilt; // use when no filter available

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

    public boolean getEmptyFilt() {
        return emptyFilt;
    }

    public void setEmptyFilt(boolean emptyFilt) {
        this.emptyFilt = emptyFilt;
    }
}
