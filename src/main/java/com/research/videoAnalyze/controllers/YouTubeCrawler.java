package com.research.videoAnalyze.controllers;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import com.research.videoAnalyze.models.LinksModel;
import com.research.videoAnalyze.models.ReturnModel;
import com.research.videoAnalyze.models.TimeModel;
import com.research.videoAnalyze.models.VideoModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YouTubeCrawler {

    private final String PROPERTIES_FILENAME = "youtube.properties";

    private YouTube youtube;
    private LinksModel linkModel;
    private List<LinksModel> links;
    private ArrayList<String> idList;
    Processes proobj;
    ReturnModel retObj;
    TimeModel timeModel;
    VideoModel videoModel;

    @Autowired
    VideoRepository repository;

    public ArrayList<String> search(HashMap<String, String> parameters) {

        links = new ArrayList<>();
        Properties properties = new Properties();

        try {
            InputStream in = YouTubeCrawler.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
            properties.load(in);

        } catch (IOException e) {
            System.err.println("There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause()
                    + " : " + e.getMessage());
            System.exit(1);
        }

        try {
            youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("videoAnalyze").build();

            // Define the API request for retrieving search results
            YouTube.Search.List search = youtube.search().list("snippet");

            if (parameters.containsKey("q") && parameters.get("q") != "") {
                search.setQ(parameters.get("q").toString());
            }
            if (parameters.containsKey("videoDefinition") && parameters.get("videoDefinition") != "") {
                search.setVideoDefinition(parameters.get("videoDefinition").toString());
            }
            if (parameters.containsKey("videoDuration") && parameters.get("videoDuration") != "") {
                search.setVideoDuration(parameters.get("videoDuration").toString());
            }
            if (parameters.containsKey("maxResults") && parameters.get("maxResults") != "") {
                search.setMaxResults(Long.parseLong(parameters.get("maxResults")));
            }

            search.setRegionCode("US");

            String apiKey = properties.getProperty("youtube.apikey");
            search.setKey(apiKey);

            // Restrict the search results to only include videos
            search.setType("video");

            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();

            if (searchResultList != null) {
                links = prettyPrint(searchResultList.iterator(), parameters.get("q"), apiKey);
            } else {
                LinksModel emptyModel = new LinksModel();
                emptyModel.setEmptyList("empty");
                links.add(emptyModel);
            }
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return idList;
    }

    private List<LinksModel> prettyPrint(Iterator<SearchResult> iteratorSearchResults, String query, String key) {

        ArrayList<LinksModel> linkList = new ArrayList<>();
        idList = new ArrayList<>();

        if (!iteratorSearchResults.hasNext()) {
            System.out.println(" There aren't any results for your query.");
        }

        while (iteratorSearchResults.hasNext()) {

            SearchResult singleVideo = iteratorSearchResults.next();
            ResourceId rId = singleVideo.getId();

            Thumbnail thumbnail;
            String videoDuration;

            if (rId.getKind().equals("youtube#video")) {

                linkModel = new LinksModel();

                idList.add(rId.getVideoId());
                getDuration(rId.getVideoId(), key);

                thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();
                videoDuration = getDuration(rId.getVideoId(), key);

                linkModel.setUrl("https://www.youtube.com/watch?v=" + rId.getVideoId());
                linkModel.setTitle(singleVideo.getSnippet().getTitle());
                linkModel.setDescription(singleVideo.getSnippet().getDescription());
                linkModel.setDuration(videoDuration);
                linkModel.setThumbnail(thumbnail.getUrl());

                linkList.add(linkModel);
            }
        }

        return linkList;
    }

    private String getDuration(String id, String apiKey) {
        String duration = "";
        String url = "https://www.googleapis.com/youtube/v3/videos?part=contentDetails&id=" + id + "&key=" + apiKey;

        JSONObject itemJsonObject = null;

        try {
            itemJsonObject = new JSONObject(sendDurationRequest(url).replace("[", "").replace("]", ""));
            duration = itemJsonObject.getJSONObject("items").getJSONObject("contentDetails").getString("duration");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertTime(duration);
    }

    private String sendDurationRequest(String youtubeUrl) {

        String USER_AGENT = "Mozilla/5.0";
        String res = "";
        URL obj = null;
        try {
            obj = new URL(youtubeUrl);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            res = response.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    private String convertTime(String encTime) {

        String pattern = new String("^PT(?:(\\d+)H)?(?:(\\d+)M)?(?:(\\d+))S?$");

        Pattern r = Pattern.compile(pattern);
        String result;

        Matcher m = r.matcher(encTime);
        if (m.find()) {
            String hh = m.group(1);
            String mm = m.group(2);
            String ss = m.group(3);
            mm = mm != null ? mm : "0";
            ss = ss != null ? ss : "0";
            result = String.format("%02d:%02d", Integer.parseInt(mm), Integer.parseInt(ss));

            if (hh != null) {
                result = hh + ":" + result;
            }
        } else {
            result = "00:00";
        }
        return result;

    }

    public ReturnModel getDetailsById(String id) {

        youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            public void initialize(HttpRequest request) {
            }
        }).setApplicationName("videoAnalyze").build();

        Properties properties = new Properties();

        try {
            InputStream in = YouTubeCrawler.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
            properties.load(in);

        } catch (IOException e) {
            System.err.println("There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause()
                    + " : " + e.getMessage());
            System.exit(1);
        }

        try {
            String apiKey = properties.getProperty("youtube.apikey");

            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("part", "snippet,contentDetails");
            parameters.put("id", id);

            YouTube.Videos.List videosListByIdRequest = youtube.videos().list(parameters.get("part").toString());
            if (parameters.containsKey("id") && parameters.get("id") != "") {
                videosListByIdRequest.setId(parameters.get("id").toString());
            }

            videosListByIdRequest.setKey(apiKey);

            VideoListResponse response = videosListByIdRequest.execute();

            proobj = new Processes();

            String duration = convertTime(response.getItems().get(0).getContentDetails().getDuration());
            String definition = response.getItems().get(0).getContentDetails().getDefinition();
            String title = response.getItems().get(0).getSnippet().getTitle();
            String description = response.getItems().get(0).getSnippet().getDescription();
            List<String> tags = response.getItems().get(0).getSnippet().getTags();
            String thumbnailUrl = response.getItems().get(0).getSnippet().getThumbnails().getHigh().getUrl();
            String videoUrl = "https://www.youtube.com/watch?v=" + id;

            System.out.println("duration " + duration);
            System.out.println("definition " + definition);
            System.out.println("title " + title);
            System.out.println("description " + description);
            System.out.println("tags " + tags);
            System.out.println("thumbnailUrl " + thumbnailUrl);
            System.out.println("videoUrl " + videoUrl);

            List<String> Keywords = proobj.generateSimilarKeywords(title);
            String searchKeywords = null;
            if (Keywords != null) {
                searchKeywords = String.join(",", Keywords) + ",";
            }

            searchKeywords = searchKeywords.concat(String.join(",", tags));
            boolean filter1, duration1, duration2, duration3;

            if (definition.contains("hd")) {
                filter1 = true;
            } else {
                filter1 = false;
            }

            timeModel = comapreTime(duration);

            duration1 = timeModel.getFilter1();
            duration2 = timeModel.getFilter2();
            duration3 = timeModel.getFilter3();

            Query query = new Query();
            query.addCriteria(Criteria.where("videoUrl").is(videoUrl));

            Update update = new Update();
            update.set("title", title);
            update.set("thumbnailUrl", thumbnailUrl);
            update.set("duration", duration);
            update.set("description", description);
            update.set("searchKeywords", searchKeywords);
            update.set("filter1", filter1);
            update.set("duration1", duration1);
            update.set("duration2", duration2);
            update.set("duration3", duration3);

            retObj = new ReturnModel();
            retObj.setVideourl(videoUrl);
            retObj.setUpdate(update);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return retObj;

    }

    public TimeModel comapreTime(String duration) {

        timeModel = new TimeModel();
        String time = null;

        System.out.println("duration before format : " + duration);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        ;

        LocalTime shortDuration = LocalTime.parse("00:04:00", dateTimeFormatter);
        LocalTime mediumDuration = LocalTime.parse("00:20:00", dateTimeFormatter);

        LocalTime durationFromTime = null;
        List<String> times = Arrays.asList(duration.split(":"));

        if (times.size() == 3) {
            if (times.get(0).length() != 2) {
                time = "0" + duration;
            } else {
                time = duration;
            }
        } else if (times.size() == 2) {
            if (times.get(0).length() != 2) {
                time = "00:0" + duration;
            } else {
                time = "00:" + duration;
            }
        } else if (times.size() == 1) {
            if (times.get(0).length() != 2) {
                time = "00:00:0" + duration;
            } else {
                time = "00:00:" + duration;
            }
        }

        System.out.println("formatted time is : " + time);

        try {
            durationFromTime = LocalTime.parse(time, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }

        System.out.println(durationFromTime);

        if (durationFromTime.getMinute() <= shortDuration.getMinute()) {
            timeModel.setFilter1(true);
            timeModel.setFilter2(false);
            timeModel.setFilter3(false);
        } else if (durationFromTime.getMinute() > shortDuration.getMinute() && durationFromTime.getMinute() <= mediumDuration.getMinute()) {
            timeModel.setFilter2(true);
            timeModel.setFilter1(false);
            timeModel.setFilter3(false);
        } else if (durationFromTime.getMinute() > mediumDuration.getMinute()) {
            timeModel.setFilter3(true);
            timeModel.setFilter1(false);
            timeModel.setFilter2(false);
        }

        return timeModel;
    }

}
