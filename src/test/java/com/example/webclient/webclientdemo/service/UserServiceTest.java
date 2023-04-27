package com.example.webclient.webclientdemo.service;
import com.example.webclient.webclientdemo.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec uriSpecMock;

    @Mock
    private WebClient.ResponseSpec responseSpecMock;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldReturnUserById() {
        // Given
        Long id = 1L;
        User expectedUser = new User(1L, "Leanne Graham", "Sincere@april.biz");

        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(uriSpecMock);
        when(uriSpecMock.uri(anyString(), eq(id))).thenReturn(uriSpecMock);
        when(uriSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(eq(User.class))).thenReturn(Mono.just(expectedUser));

        // When
        User result = userService.getUserById(id);

        // Then
        assertThat(result).isEqualTo(expectedUser);
    }

}