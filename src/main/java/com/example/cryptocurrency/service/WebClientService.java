package com.example.cryptocurrency.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WebClientService {
    public WebClient.ResponseSpec getResponseSpec(String url) {
        WebClient webClient = WebClient.create(url);
        return webClient.get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();
    }
}
