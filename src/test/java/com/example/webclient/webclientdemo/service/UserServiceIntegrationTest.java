package com.example.webclient.webclientdemo.service;

import com.example.webclient.webclientdemo.exception.CustomException;
import com.example.webclient.webclientdemo.model.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
class UserServiceIntegrationTest {
    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

   @Autowired
    private UserService userService;

    @Test
    void shouldReturnUserById() throws CustomException {
        // Given
        Long id = 1L;
        User expectedUser = new User(1L, "Leanne Graham", "Sincere@april.biz");

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(User.class)).thenReturn(Mono.just(expectedUser));
        when(responseSpec.onStatus(any(Predicate.class), any())).thenReturn(responseSpec);


        // When
        User result = userService.getUserById(id);

        // Then
        assertThat(result.getEmail()).isEqualTo(expectedUser.getEmail());
    }

    @Test
    void getById_notFound() {
        // given
        Long id = 1L;
        Predicate<HttpStatus> isBadRequest = status -> status.equals(HttpStatus.BAD_REQUEST);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);

        when(responseSpec.bodyToMono(User.class))
                .thenThrow(WebClientResponseException.create(HttpStatus.NOT_FOUND.value(),
                        "Not Found", null, null, null));
        // assert
        assertThrows(CustomException.class, () -> userService.getUserById(id));
    }
    /*
    no funciono el status response

    @Test
    public void testHandle400Error() {

        when(responseSpec.onStatus(any(Predicate.class), any())).thenAnswer(invocation -> {
            Predicate<HttpStatus> predicate = invocation.getArgument(0);
            if (predicate.test(HttpStatus.BAD_REQUEST)) {
                // Lógica para el estado HttpStatus.BAD_REQUEST
                // Por ejemplo, devolver un Mono de error
                return responseSpec.bodyToMono(User.class).thenReturn(Mono.error(new CustomException("Error 400")));
            } else {
                // Lógica para otros estados
                // Por ejemplo, devolver el mismo responseSpec
                return responseSpec;
            }
        });

        // Ejemplo de uso del mock
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);

        assertThrows(CustomException.class, () -> userService.getUserById(1L));

        // Assert the behavior or result as needed
        // ...
    }

     */
}
