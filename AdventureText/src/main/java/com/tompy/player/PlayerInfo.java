package com.tompy.player;

import java.util.HashMap;
import java.util.Map;

public class PlayerInfo {
    public static final String VISITS = "VISITS";
    public static final String SEARCHES = "SEARCHES";
    private Map<String, Integer> countMap = new HashMap<>();

    public int getCount(String key) {
        int count = 0;
        if (countMap.containsKey(key)) {
            count = countMap.get(key);
        }
        return count;
    }

    public void incrementCount(String key) {
        int count = 1;
        if (countMap.containsKey(key)) {
            count += countMap.get(key);
        }
        countMap.put(key, count);
    }
}
