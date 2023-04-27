package com.example.webclient.webclientdemo.client;

import com.example.webclient.webclientdemo.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@WebFluxTest(UserClient.class)
public class UserClientTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testGetUserById() {
        User expectedUser = new User(1L, "Leanne Graham", "Sincere@april.biz");

        webTestClient.get().uri("/users/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .isEqualTo(expectedUser);
    }
}