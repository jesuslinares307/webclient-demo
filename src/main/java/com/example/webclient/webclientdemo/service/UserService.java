package com.example.webclient.webclientdemo.service;

import com.example.webclient.webclientdemo.exception.CustomException;
import com.example.webclient.webclientdemo.model.User;
import io.netty.handler.timeout.ReadTimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    private final WebClient webClientBuilder;

    public UserService(WebClient webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public User getUserById(Long id) throws CustomException {
        try {
            return webClientBuilder
                    .get()
                    .uri("https://jsonplaceholder.typicode.com/users/{id}", id)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, response -> {
                        System.out.println("Error 4xx: " + response.statusCode());
                        return Mono.error(new CustomException("Error 4xx"));
                    })
                    .onStatus(HttpStatus::is5xxServerError, response -> {
                        System.out.println("Error 5xx: " + response.statusCode());
                        return Mono.error(new CustomException("Error 5xx"));
                    })
                    .bodyToMono(User.class)
                .block();
        } catch (Exception e) {
            if (e instanceof ReadTimeoutException) {
                throw new CustomException("Timeout: " + e.getMessage());
            } else {
                throw new CustomException("Error: " + e.getMessage());
            }
        }
    }
}