package com.example.demokafkaproducer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NytTopStory {
    public String getTitle() {
        return title;
    }

    public String getStoryAbstract() {
        return storyAbstract;
    }

    public String getUrl() {
        return url;
    }

    @JsonProperty("title")
    private String title;

    @JsonProperty("abstract")
    private String storyAbstract;

    @JsonProperty("url")
    private String url;
}
