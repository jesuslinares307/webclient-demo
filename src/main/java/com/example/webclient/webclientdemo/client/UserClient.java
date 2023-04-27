package com.example.webclient.webclientdemo.client;

import com.example.webclient.webclientdemo.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserClient {
    private WebClient webClient;

    public UserClient() {
        this.webClient = WebClient.create("https://jsonplaceholder.typicode.com");
    }

    public Mono<User> getUserById(Long id) {
        return webClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .bodyToMono(User.class);
    }
}