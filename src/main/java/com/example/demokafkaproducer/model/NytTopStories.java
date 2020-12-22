package com.example.demokafkaproducer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NytTopStories {
    private static final Logger logger = LogManager.getLogger(NytTopStories.class);

    @JsonProperty("results")
    private List<NytTopStory> stories;

    public List<NytTopStory> getStories() {
        return stories;
    }
}
