package com.rogerfitness.workoutsystem.clients.googleimages;

import com.rogerfitness.workoutsystem.apis.GoogleInlineImagesResponse;
import com.rogerfitness.workoutsystem.clients.QueryParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.rogerfitness.workoutsystem.clients.googleimages.GoogleImagesQueryParams.GoogleImagesQueryParamKey.GOOGLE_DOMAIN;
import static com.rogerfitness.workoutsystem.clients.googleimages.GoogleImagesQueryParams.GoogleImagesQueryParamKey.SEARCH;

@Slf4j
@Service
public class GoogleImageClientWrapper {
    private final String GOOGLE_DOMAIN_VALUE = "google.com";
    private final GoogleImagesClient googleImagesClient;

    public GoogleImageClientWrapper(GoogleImagesClient googleImagesClient) {
        this.googleImagesClient = googleImagesClient;
    }

    public GoogleInlineImagesResponse fetchInlineImages(String imageToSearch) {
        try {
            return googleImagesClient.fetchInlineImage(buildGoogleImagesParams(imageToSearch)).block();
        } catch (Exception exception) {
            log.error("Error when calling GoogleImageInlines when trying to fetch images of {}", imageToSearch, exception);
            throw exception;
        }
    }

    private QueryParams<GoogleImagesQueryParams.GoogleImagesQueryParamKey> buildGoogleImagesParams(String search) {
        return GoogleImagesQueryParams
                .builder()
                .param(SEARCH, search)
                .param(GOOGLE_DOMAIN, GOOGLE_DOMAIN_VALUE)
                .build();
    }
}
