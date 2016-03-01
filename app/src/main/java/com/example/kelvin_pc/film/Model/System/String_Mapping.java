package com.example.kelvin_pc.film.Model.System;

import java.util.HashMap;

public class String_Mapping {

    public HashMap<String, String> logicMap, categoryMap, sortMap;

    public String_Mapping() {
        initVariables();
        initGreater();
        initCategory();
        initSort();
    }

    public void initVariables() {
        logicMap = new HashMap<>();
        categoryMap = new HashMap<>();
        sortMap = new HashMap<>();
    }

    public void initGreater() {
        logicMap.put("Greater", ".gte");
        logicMap.put("Lesser", ".lte");
    }

    public void initCategory() {
        categoryMap.put("Now Playing", "now_playing");
        categoryMap.put("Popular", "popular");
        categoryMap.put("Top Rated", "top_rated");
        categoryMap.put("Upcoming", "upcoming");
    }

    public void initSort() {
        sortMap.put("Popularity", "popularity");
        sortMap.put("Release Date", "release_date");
        sortMap.put("Revenue", "revenue");
        sortMap.put("Title", "original_title");
        sortMap.put("Rating", "vote_average");
        sortMap.put("Votes", "vote_count");
    }


}
