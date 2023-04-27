package com.example.webclient.webclientdemo.service;

import com.example.webclient.webclientdemo.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    private final WebClient webClientBuilder;

    public UserService(WebClient webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public User getUserById(Long id) {
        return webClientBuilder
                .get()
                .uri("https://jsonplaceholder.typicode.com/users/{id}", id)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }
}