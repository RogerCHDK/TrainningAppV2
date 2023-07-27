package com.rogerfitness.workoutsystem.clients.googleimages;

import com.rogerfitness.workoutsystem.clients.QueryParams;

import java.util.List;
import java.util.Map;

public class GoogleImagesQueryParams extends QueryParams<GoogleImagesQueryParams.GoogleImagesQueryParamKey> {
    public GoogleImagesQueryParams(Map<GoogleImagesQueryParamKey, List<String>> params) {
        super(params);
    }

    public static QueryParamsBuilder<GoogleImagesQueryParamKey> builder() {
        return new QueryParamsBuilder<>();
    }

    public enum GoogleImagesQueryParamKey {
        SEARCH("q"),
        GOOGLE_DOMAIN("google_domain");
        private final String keyName;

        GoogleImagesQueryParamKey(String keyName) {
            this.keyName = keyName;
        }

        @Override
        public String toString() {
            return this.keyName;
        }
    }
}
