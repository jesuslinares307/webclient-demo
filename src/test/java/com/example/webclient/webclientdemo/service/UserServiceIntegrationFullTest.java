package com.example.webclient.webclientdemo.service;

import com.example.webclient.webclientdemo.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class UserServiceIntegrationFullTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @InjectMocks
    private UserService userService;

    @Test
    public void getUserByIdTest() {
        // Configurar el objeto WebClient.Builder simulado para devolver una respuesta simulada
        User expectedUser = new User(1L, "Leanne Graham", "Sincere@april.biz");

        when(webClientBuilder.build().get()
                .uri("https://jsonplaceholder.typicode.com/users/1")
                .retrieve()
                .bodyToMono(User.class))
                .thenReturn(Mono.just(expectedUser));

        // Ejecutar la prueba
        User actualUser = userService.getUserById(1L);

        // Verificar que se haya realizado la solicitud simulada y que la respuesta sea la esperada
        verify(webClientBuilder.build().get()
                .uri("https://jsonplaceholder.typicode.com/users/1")
                .retrieve()
                .bodyToMono(User.class)).block();
        assertEquals(expectedUser, actualUser);
    }
}