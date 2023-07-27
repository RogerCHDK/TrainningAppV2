package com.rogerfitness.workoutsystem.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {
    @Value("${google.inline.images.base.url}")
    private String googleInlineImagesBaseUrl;
    private final ExchangeStrategies exchangeStrategies = ExchangeStrategies
            .builder()
            .codecs(clientCodecConfigurer -> {
                ObjectMapper halObjectMapper = new ObjectMapper();
                halObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                halObjectMapper.registerModules(new Jackson2HalModule(), new JavaTimeModule());
                clientCodecConfigurer
                        .defaultCodecs()
                        .jackson2JsonDecoder(new Jackson2JsonDecoder(halObjectMapper, MediaType.ALL));
                clientCodecConfigurer
                        .defaultCodecs()
                        .jackson2JsonEncoder(new Jackson2JsonEncoder(halObjectMapper, MediaType.ALL));

            }).build();

    private HttpClient httpClient(){
        return HttpClient.create();
    }

    WebClient webClient() {
        return WebClient
                .builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient()))
                .exchangeStrategies(exchangeStrategies)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                })
                .build();
    }

    @Bean(name = "googleInlineImagesWebClient")
    WebClient googleInlineImagesWebClient() {
        return webClient().mutate().baseUrl(googleInlineImagesBaseUrl).build();
    }

}
