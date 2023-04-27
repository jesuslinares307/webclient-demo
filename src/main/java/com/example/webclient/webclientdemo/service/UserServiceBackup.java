package com.example.webclient.webclientdemo.service;

import com.example.webclient.webclientdemo.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserServiceBackup {
    private final WebClient.Builder webClientBuilder;

    public UserServiceBackup(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public User getUserById(Long id) {
        return webClientBuilder.build()
                .get()
                .uri("https://jsonplaceholder.typicode.com/users/{id}", id)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }
}