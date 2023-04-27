package com.example.webclient.webclientdemo.service;

import com.example.webclient.webclientdemo.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private WebClient.Builder webClientBuilder;

    @Test
    void shouldReturnUserById() {
        // Given
        Long id = 1L;
        User expectedUser = new User(1L, "Leanne Graham", "Sincere@april.biz");
        expectedUser.setId(1L);
        expectedUser.setName("magdy");
        expectedUser.setEmail("mag");

        WebClient webClient = Mockito.mock(WebClient.class);
        WebClient.RequestHeadersUriSpec requestHeadersUriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);
        Mockito.when(webClientBuilder.build()).thenReturn(webClient);
        Mockito.when(webClient.get()).thenReturn(requestHeadersUriSpec);
        Mockito.when(requestHeadersUriSpec.uri(Mockito.anyString(), Mockito.anyLong())).thenReturn(requestHeadersUriSpec);
        Mockito.when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        Mockito.when(responseSpec.bodyToMono(User.class)).thenReturn(Mono.just(expectedUser));

        // When
        User result = webTestClient
                .get()
                .uri("http://localhost:{port}/users/{id}", port, id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .returnResult().getResponseBody();

        // Then
        assertThat(result.getEmail()).isEqualTo(expectedUser.getEmail());
    }

}
