package com.research.videoAnalyze.controllers;

import com.research.videoAnalyze.models.FilterModel;
import com.research.videoAnalyze.models.VideoURLDAO;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Processes {

    HashMap<String, String> parameters;
    private ArrayList<String> idList;

    public FilterModel separateFilters(List<String> filtList) {

        System.out.println(filtList);

        FilterModel filtModel = new FilterModel();
        if (filtList != null) {
            for (String value : filtList) {

                if (value.contains("quality")) {
                    filtModel.setFilter1("true");
                    //filterLsMap.put("quality", "high");
                }
                if (value.contains("presenter")) {
                    filtModel.setFilter2("true");
                    //filterLsMap.put("presenter", "enable");
                }
                if (value.contains("code visible")) {
                    filtModel.setFilter3("true");
                    //filterLsMap.put("code", "enable");
                }
                if (value.contains("Eclipse")) {
                    filtModel.setFilter4("true");
                }
                if (value.contains("Intellij")) {
                    filtModel.setFilter5("true");
                }
                if (value.contains("Visual studio")) {
                    filtModel.setFilter6("true");
                }
                if (value.contains("Net beans")) {
                    filtModel.setFilter7("true");
                }
                if (value.contains("Visual studio code")) {
                    filtModel.setFilter8("true");
                }
                if (value.contains("Android")) {
                    filtModel.setFilter9("true");
                }
                if (value.contains("duration > 20")) {
                    filtModel.setDuration1("true");
                    //filterLsMap.put("duration", "long");
                }
                if (value.contains("duration < 4")) {
                    filtModel.setDuration2("true");
                    //filterLsMap.put("duration", "short");
                }
                if (value.contains("duration (4 - 20)")) {
                    filtModel.setDuration3("true");
                    //filterLsMap.put("duration", "medium");
                }
                filtModel.setEmptyFilt(false);
            }
        } else {
            filtModel.setEmptyFilt(true);
        }

        return filtModel;
    }

    public List<String> getYouTubeVideoList(FilterModel filtDAO, String keyword, List<VideoURLDAO> vidsIdList, String noOfResults) {

        parameters = new HashMap<>();
        idList = new ArrayList<>();
        String duration;//any,long,medium,short
        List<String> searchLinkList = new ArrayList<>();

        if (filtDAO.getDuration1() == null && filtDAO.getDuration2() == null && filtDAO.getDuration3() == null) {
            duration = "any";
        } else if (filtDAO.getDuration1() != null && filtDAO.getDuration2() == null && filtDAO.getDuration3() == null) {
            duration = "long";
        } else if (filtDAO.getDuration1() == null && filtDAO.getDuration2() != null && filtDAO.getDuration3() == null) {
            duration = "short";
        } else if (filtDAO.getDuration1() == null && filtDAO.getDuration2() == null && filtDAO.getDuration3() != null) {
            duration = "medium";
        } else {
            duration = "any";
        }

        String quality;//any,high,standard
        if (filtDAO.getFilter1() == "HD") {
            quality = "high";
        } else {
            quality = "any";
        }

        parameters = new HashMap<>();
        parameters.put("q", keyword);
        parameters.put("videoDuration", duration);
        parameters.put("videoDefinition", quality);
        parameters.put("maxResults", noOfResults);

        System.out.println("no of results "+ noOfResults);

        //get list of link ids from youtube
        idList = new YouTubeCrawler().search(parameters);

        int i = 0;
        for (String id : idList) {
            id = "https://www.youtube.com/watch?v=" + id;
            idList.set(i, id);
            i++;
        }

        //check does the identified urls already processed
        int y = 0;
        for (String id : idList) {
            if (y >= 5) {
                break;
            }
            else if (!vidsIdList.contains(id)) {
                searchLinkList.add(id);
                y++;
            }
        }

        for(String id: searchLinkList){
            System.out.println(id);
        }

        if (searchLinkList.size() != 5) {
            getYouTubeVideoList(filtDAO, keyword, vidsIdList, Integer.toString(Integer.parseInt(noOfResults) + 5));
        }

        return searchLinkList;

    }

}
