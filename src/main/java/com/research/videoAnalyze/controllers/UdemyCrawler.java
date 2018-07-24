package com.research.videoAnalyze.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class UdemyCrawler {

    private static HttpURLConnection con;
    final String URL = "https://www.udemy.com/api-2.0/courses/?search=java";

    public void searchUdemy() {

        System.out.println("Testing 1 - Send Http GET request");

        try {
            sendGet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // HTTP GET request
    private void sendGet() throws Exception {

        try {
            URL myurl = new URL(URL);
            con = (HttpURLConnection) myurl.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization","Basic TUcwWUd6Q3V4WVpEOGRlTGJzQjdyMjZRSE0weTZFSDV2dHdLckZtNjpBY0c3OURiVGt1aG9UTDU1TXJncUxSM1p0czlUTTJlREh2cXh3SE8zQ1o0SHNaUE1td2Q5QWFlVWYzSUpYakhMMXN0S0RhWUVZTFFtWE5vNVhseUN3TGRaOE1yd3h2bjZjWVFRNGJrVmVoMU5Md3pqaEVBbTBSSnNMZ1dLMm9VYg==");
            StringBuilder content;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

            System.out.println(content.toString());

        } finally {
            con.disconnect();
        }

    }

    public String setParameters(HashMap<String, String> params) throws UnsupportedEncodingException {

        //?duration=short&ordering=highest-rated&price=price-free&search=java
        /*
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
         */
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString;
    }

}
