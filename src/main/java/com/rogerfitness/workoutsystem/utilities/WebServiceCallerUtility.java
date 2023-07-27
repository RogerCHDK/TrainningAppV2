package com.rogerfitness.workoutsystem.utilities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

import java.io.IOException;
import java.time.Duration;
import java.util.Optional;
@Slf4j
@Component
public class WebServiceCallerUtility {
    @Value("${external.api.maxRetry}")
    private int maxAttempts;
    @Value("${external.api.delay}")
    private int delay;
    public Retry retryPolicy(){
        return Retry.fixedDelay(maxAttempts, Duration.ofMillis(delay))
                .filter(this::isRetryable)
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> retrySignal.failure());
    }

    public boolean isRetryable(Throwable throwable) {
        if (throwable instanceof WebClientResponseException webClientResponseException) {
            String callName = Optional.ofNullable(webClientResponseException.getRequest())
                    .map(httpRequest -> String.format("%s: %s", httpRequest.getMethodValue(), httpRequest.getURI()))
                    .orElse("");
            log.warn("Service call failure [{}], statusCode={}, responseBody={}",
                    callName,
                    webClientResponseException.getStatusCode(),
                    webClientResponseException.getResponseBodyAsString()
            );
            return webClientResponseException.getStatusCode().is5xxServerError();
        }
        log.warn("Failed to complete service call: {}", throwable.getMessage());
        return throwable instanceof IOException;
    }

    public String createServiceId(String path){
        return String.format("%s: %s", HttpMethod.GET.name(), path);
    }
}
