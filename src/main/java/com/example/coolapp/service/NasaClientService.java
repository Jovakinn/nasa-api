package com.example.coolapp.service;

import com.example.coolapp.dto.Picture;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Objects;
import java.util.stream.StreamSupport;

import static java.util.Comparator.comparing;

@Service
@RequiredArgsConstructor
@Slf4j
public class NasaClientService {
    @Value("${nasa.api.url}")
    private String nasaApiUrl;
    private final RestTemplate restTemplate;


    @Cacheable("largestPicture")
    public Picture getLargestPicture() {
        var jsonResponse = restTemplate.getForObject(nasaApiUrl, JsonNode.class);
        return StreamSupport.stream(Objects.requireNonNull
                        (jsonResponse).get("photos").spliterator(), true)
                .map(photo -> photo.get("img_src"))
                .map(JsonNode::asText)
                .map(this::createPicture)
                .max(comparing(Picture::size))
                .orElseThrow();
    }

    private Picture createPicture(String pictureUrl) {
        var pictureLocation = restTemplate.headForHeaders(pictureUrl).getLocation();
        var pictureSize = restTemplate.headForHeaders
                (Objects.requireNonNull(pictureLocation)).getContentLength();
        return new Picture(pictureUrl, pictureSize);
    }
}
