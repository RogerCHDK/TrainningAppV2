package com.rogerfitness.workoutsystem.clients.googleimages;

import com.rogerfitness.workoutsystem.apis.GoogleInlineImagesResponse;
import com.rogerfitness.workoutsystem.clients.QueryParams;
import com.rogerfitness.workoutsystem.exceptions.webservice.GoogleInlineImagesException;
import com.rogerfitness.workoutsystem.utilities.WebServiceCallerUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
@Slf4j
public class GoogleImagesClient {
    private final String API_KEY_HEADER_NAME = "api_key";

    @Value("${google.inline.images.api.key}")
    private String apiKey;
    @Value("${google.inline.images.path}")
    private String googleInlineImagesPath;

    @Qualifier("googleInlineImagesWebClient")
    private final WebClient webClient;

    private final WebServiceCallerUtility webServiceCallerUtility;

    public GoogleImagesClient(WebClient webClient, WebServiceCallerUtility webServiceCallerUtility) {
        this.webClient = webClient;
        this.webServiceCallerUtility = webServiceCallerUtility;
    }

    public Mono<GoogleInlineImagesResponse> fetchInlineImage(QueryParams<GoogleImagesQueryParams.GoogleImagesQueryParamKey> queryParams){
        return webClient
                .get()
                .uri(uriBuilder -> {
                    URI uri = uriBuilder
                            .path(googleInlineImagesPath)
                            .queryParam(API_KEY_HEADER_NAME, apiKey)
                            .queryParams(queryParams.getParams())
                            .build();
                    log.info("service call to GoogleImagesClient GET [{}]", uri);
                    return uri;
                })
                .retrieve()
                .bodyToMono(GoogleInlineImagesResponse.class)
                .doOnSubscribe(subscription -> log.info("fetching Google Images {}", subscription))
                .retryWhen(webServiceCallerUtility.retryPolicy())
                .onErrorMap(throwable -> new GoogleInlineImagesException(throwable, webServiceCallerUtility.createServiceId(googleInlineImagesPath)));
    }
}
