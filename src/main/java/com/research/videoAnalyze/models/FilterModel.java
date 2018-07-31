package com.research.videoAnalyze.models;

import com.google.api.services.youtube.YouTube;

import java.util.List;

public class FilterModel {

    private boolean duration1;// >20
    private boolean duration2;// <4
    private boolean duration3;// 4-20
    private boolean filter1;// quality(resolution HD)
    private boolean filter2;// presenter visible in  the video
    private boolean filter3;// code visible in the video
    private boolean filter4;// IDE : Eclipse
    private boolean filter5;// IDE : Intellij (Idea, Pycharm, webstorm)
    private boolean filter6;// IDE : Visual studio
    private boolean filter7;// IDE : Net beans
    private boolean filter8;// IDE : Visual studio Code
    private boolean filter9;// IDE : Android studio
    private boolean emptyFilt; // use when no filter available

    public boolean getDuration1() {
        return duration1;
    }

    public void setDuration1(boolean duration1) {
        this.duration1 = duration1;
    }

    public boolean getDuration2() {
        return duration2;
    }

    public void setDuration2(boolean duration2) {
        this.duration2 = duration2;
    }

    public boolean getDuration3() {
        return duration3;
    }

    public void setDuration3(boolean duration3) {
        this.duration3 = duration3;
    }

    public boolean getFilter1() {
        return filter1;
    }

    public void setFilter1(boolean filter1) {
        this.filter1 = filter1;
    }

    public boolean getFilter2() {
        return filter2;
    }

    public void setFilter2(boolean filter2) {
        this.filter2 = filter2;
    }

    public boolean getFilter3() {
        return filter3;
    }

    public void setFilter3(boolean filter3) {
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

    public boolean getEmptyFilt() {
        return emptyFilt;
    }

    public void setEmptyFilt(boolean emptyFilt) {
        this.emptyFilt = emptyFilt;
    }
}
