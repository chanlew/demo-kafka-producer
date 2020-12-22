package com.example.demokafkaproducer.util;

import com.example.demokafkaproducer.model.NytTopStories;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

public class MessageSource {
    private static final Logger logger = LogManager.getLogger(MessageSource.class);

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.nytimes.com")
            .build();

    public List<String> getMessages() {

        Mono<NytTopStories> topStoriesMono = webClient.get()
                .uri("/svc/topstories/v2/home.json?api-key=jGDdcMHdoqin7i3V3w4aQHaHczcuyOmO")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve().bodyToMono(NytTopStories.class);

        ObjectMapper mapper = new ObjectMapper();

        List<String> messages = topStoriesMono.block()
                .getStories()
                .stream()
                .map(s -> {
                    String json = null;
                    try {
                        json = mapper.writeValueAsString(s);
                    } catch (JsonProcessingException e) {
                        logger.error("Error serializing to Json {}: {}", s, e.getMessage());
                    }
                    return json;
                })
                .filter(s -> s != null)
                .collect(Collectors.toList());

        return messages;
    }
}
