package br.com.wallet.client;

import br.com.wallet.dto.UserResponseDto;
import br.com.wallet.util.RetryBackoffUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.client.WebClientResponseException.BadRequest;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.client.WebClientResponseException.NotFound;

@Slf4j
@Component
public class UserClient {

    private final WebClient webClient;

    public UserClient(@Value("${ms.user.url}") final String url) {
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .filter(logRequest())
                .build();
    }

    public Mono<UserResponseDto> getUser(String userId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/users").pathSegment(userId).build())
                .retrieve()
                .bodyToMono(UserResponseDto.class)
                .retryWhen(RetryBackoffUtil.getRetryBackoffSpecification())
                .onErrorResume(WebClientResponseException.class, UserClient::handleResponseErrors);
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());

            return Mono.just(clientRequest);
        });
    }

    private static <T> Mono<T> handleResponseErrors(WebClientResponseException exception) {
        return exception instanceof BadRequest || exception instanceof NotFound
                ? Mono.empty() : Mono.error(exception);
    }

}
