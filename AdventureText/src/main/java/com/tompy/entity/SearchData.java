package com.tompy.entity;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SearchData {
    List<? extends Entity> items;
    String description;

    public SearchData(List<? extends Entity> items, String description) {
        this.items = Objects.requireNonNull(items, "List of Entities cannot be null.");
        this.description = Objects.requireNonNull(description, "Description cannot be null.");
    }

    public List<? extends Entity> getItems() {
        return items;
    }

    public String getDescription() {
        return description;
    }

    public interface ScoreComputer {
        Map<Long, Integer> compute(SearchData data);
    }
}
