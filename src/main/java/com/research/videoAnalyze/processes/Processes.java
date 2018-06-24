package com.research.videoAnalyze.processes;

import com.research.videoAnalyze.models.SearchModel;

import java.util.HashMap;
import java.util.List;

public class Processes {

    HashMap<String, String> filterLsMap;

    public HashMap<String, String> separateFilters(List<String> filtlist) {

        String value;
        int i = 0;
        filterLsMap = new HashMap<>();

        while (i < filtlist.size()) {
            value = filtlist.get(i);

            if (value.contains("Eclipse")) {
                filterLsMap.put("eclipse", "eclipse");
            }
            else if (value.contains("android")) {
                filterLsMap.put("android", "android");
            }
            else if (value.contains("Intellij")) {
                filterLsMap.put("intellij", "intellij");
            }
            else if (value.contains("Visual studio")) {
                filterLsMap.put("VisualStudio", "Visual studio");
            }
            else if (value.contains("Net beans")) {
                filterLsMap.put("netBeans", "Net beans");
            }

            if (value.contains("duration > 20")) {
                filterLsMap.put("duration", "long");
            }
            else if (value.contains("duration < 4")) {
                filterLsMap.put("duration", "short");
            }
            else if (value.contains("duration (4 - 20)")) {
                filterLsMap.put("duration", "medium");
            }

            if (value.contains("quality")) {
                filterLsMap.put("quality", "high");
            }

            if (value.contains("presenter")) {
                filterLsMap.put("presenter", "enable");
            }

            if (value.contains("code visible")) {
                filterLsMap.put("code", "enable");
            }

            i++;
        }

        return filterLsMap;
    }
}
