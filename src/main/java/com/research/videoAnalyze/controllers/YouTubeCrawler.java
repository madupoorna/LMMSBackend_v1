package com.research.videoAnalyze.controllers;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.gson.Gson;
import com.research.videoAnalyze.models.LinksModel;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YouTubeCrawler {

    private final String PROPERTIES_FILENAME = "youtube.properties";

    private String procServerUrl = "http://127.0.0.1:5000/start";

    private YouTube youtube;
    private LinksModel linkModel;
    private List<LinksModel> links;
    private ArrayList<String> idList;

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
        /*
        try {
            sendStartProcessingRequest(idList);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        */
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

    private void sendStartProcessingRequest(ArrayList<String> videoIds) throws UnsupportedEncodingException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost postRequest = new HttpPost(procServerUrl);
        Gson gson = new Gson();

        String json = gson.toJson(videoIds);
        System.out.println(json);
        postRequest.setEntity(new StringEntity(json));
        postRequest.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        try (CloseableHttpResponse httpResponse = httpClient.execute(postRequest)) {
            String content = EntityUtils.toString(httpResponse.getEntity());

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            System.out.println("statusCode = " + statusCode);
            System.out.println("content = " + content);
        } catch (IOException e) {
            //handle exception
            e.printStackTrace();
        }
    }
}
