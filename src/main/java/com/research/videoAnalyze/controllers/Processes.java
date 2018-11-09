package com.research.videoAnalyze.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.research.videoAnalyze.models.FilterModel;
import com.research.videoAnalyze.models.MailModel;
import com.research.videoAnalyze.models.OntologyModel;
import com.research.videoAnalyze.models.VideoURLDAO;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Processes {

    HashMap<String, String> parameters;
    private ArrayList<String> idList;
    MailModel mail;

    public FilterModel separateFilters(List<String> filtList) {

        FilterModel filtModel = new FilterModel();

        if (filtList != null) {
            for (String value : filtList) {

                if (value.contains("quality")) {
                    filtModel.setFilter1(true);
                    //filterLsMap.put("quality", "high");
                }
                if (value.contains("presenter")) {
                    filtModel.setFilter2(true);
                    //filterLsMap.put("presenter", "enable");
                }
                if (value.contains("code visible")) {
                    filtModel.setFilter3(true);
                    //filterLsMap.put("code", "enable");
                }
                if (value.contains("Eclipse")) {
                    filtModel.setFilter4(true);
                }
                if (value.contains("Intellij")) {
                    filtModel.setFilter5(true);
                }
                if (value.contains("Code")) {
                    filtModel.setFilter6(true);
                }
                if (value.contains("Net beans")) {
                    filtModel.setFilter7(true);
                }
                if (value.contains("Visual studio")) {
                    filtModel.setFilter8(true);
                }
                if (value.contains("Android")) {
                    filtModel.setFilter9(true);
                }
                if (value.contains("> 20")) {
                    filtModel.setDuration1(true);
                    //filterLsMap.put("duration", "long");
                }
                if (value.contains("< 4")) {
                    filtModel.setDuration2(true);
                    //filterLsMap.put("duration", "short");
                }
                if (value.contains("4 - 20")) {
                    filtModel.setDuration3(true);
                    //filterLsMap.put("duration", "medium");
                }
            }
            filtModel.setEmptyFilt(false);
        } else {
            filtModel.setEmptyFilt(true);
        }

//        System.out.println(filtModel.getFilter1());
//        System.out.println(filtModel.getFilter2());
//        System.out.println(filtModel.getFilter3());
//        System.out.println(filtModel.getFilter4());
//        System.out.println(filtModel.getFilter5());
//        System.out.println(filtModel.getFilter6());
//        System.out.println(filtModel.getFilter7());
//        System.out.println(filtModel.getFilter8());
//        System.out.println(filtModel.getFilter9());
//        System.out.println(filtModel.getDuration1());
//        System.out.println(filtModel.getDuration2());
//        System.out.println(filtModel.getDuration3());
//        System.out.println(filtModel.getEmptyFilt());

        return filtModel;
    }

    public List<String> getYouTubeVideoList(FilterModel filtDAO, String keyword, List<VideoURLDAO> vidsIdList, String noOfResults) {

        parameters = new HashMap<>();
        idList = new ArrayList<>();
        String duration;//any,long,medium,short
        List<String> searchLinkList = new ArrayList<>();

        if (filtDAO.getDuration1() == false && filtDAO.getDuration2() == false && filtDAO.getDuration3()== false) {
            duration = "any";
        } else if (filtDAO.getDuration1() == true && filtDAO.getDuration2()== false && filtDAO.getDuration3() == false) {
            duration = "long";
        } else if (filtDAO.getDuration1()== false && filtDAO.getDuration2() == true && filtDAO.getDuration3()== false) {
            duration = "short";
        } else if (filtDAO.getDuration1() == false && filtDAO.getDuration2() == false && filtDAO.getDuration3() == true) {
            duration = "medium";
        } else {
            duration = "any";
        }

        String quality;//any,high,standard
        if (filtDAO.getFilter1() == true) {
            quality = "high";
        } else {
            quality = "any";
        }

        parameters = new HashMap<>();
        parameters.put("q", keyword);
        parameters.put("videoDuration", duration);
        parameters.put("videoDefinition", quality);
        parameters.put("maxResults", noOfResults);

        System.out.println("no of results " + noOfResults);

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
            } else if (!vidsIdList.contains(id)) {
                searchLinkList.add(id);
                y++;
            }
        }

        if (searchLinkList.size() != 5) {
            getYouTubeVideoList(filtDAO, keyword, vidsIdList, Integer.toString(Integer.parseInt(noOfResults) + 3));
        }

        return searchLinkList;

    }

    public List<String> generateSimilarKeywords(String keyword) throws IOException {

        keyword = keyword.replaceAll("[^a-zA-Z0-9]", " ");

        String languageInKeyword = null;
        String text = null;
        List<String> searchKeywords = new ArrayList<>();

        String filePath = new ClassPathResource("My_Programming.owl").getFile().getCanonicalPath();
        String ontologyUrl = "http://www.semanticweb.org/nishan/ontologies/2018/5/Programming";
        OntologyHandler handler = new OntologyHandler(filePath, ontologyUrl);

        List<String> languageWords = readJson().getLanguages();

        for (String language : languageWords) {
            if (keyword.toLowerCase().trim().contains(language.toLowerCase().trim())) {
                languageInKeyword = language.trim();
                if (languageInKeyword != null) {
                    text = keyword.replace(languageInKeyword, "").trim();
                }
            }
        }

        if (languageInKeyword == null) {
            text = keyword.trim();
        }

        System.out.println(text);

        List<String> subwords = handler.getSubWordsOf(text, 10);
        List<String> similarWords = handler.getEquivalentWords(text);

        if (languageInKeyword != null) {
            for (String word : subwords) {
                searchKeywords.add(languageInKeyword + " " + word);
            }

            for (String word : similarWords) {
                searchKeywords.add(languageInKeyword + " " + word);
            }
        } else {
            for (String word : subwords) {
                searchKeywords.add(word);
            }

            for (String word : similarWords) {
                searchKeywords.add(word);
            }
        }
        return searchKeywords;
    }

    private OntologyModel readJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        OntologyModel words = objectMapper.readValue(new ClassPathResource("words.json").getFile(), OntologyModel.class);
        return words;
    }

    public void sendEmail(String receiver, String url){
        mail = new MailModel();
        mail.setFrom("username@gmail.com");
        mail.setTo(receiver);
        mail.setSubject("LMMS Application your requested data");
        mail.setContent("Thanks for your patience. You can use following link to access to your requested data."+ url);

        //EmailService.sendSimpleMessage(mail);
    }

}
