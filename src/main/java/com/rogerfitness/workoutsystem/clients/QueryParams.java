package com.rogerfitness.workoutsystem.clients;

import lombok.EqualsAndHashCode;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@EqualsAndHashCode
public class QueryParams<T> {
    private final Map<T, List<String>> params;

    public QueryParams(Map<T, List<String>> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return params.entrySet().stream()
                .sorted(Comparator.comparing(tListEntry -> tListEntry.getKey().toString()))
                .filter(tListEntry -> Objects.nonNull(tListEntry.getKey()))
                .map(tListEntry -> tListEntry.getKey() + ": " + String.join(",", tListEntry.getValue()))
                .collect(Collectors.joining(", "));
    }

    public MultiValueMap<String, String> getParams(){
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        params.entrySet().stream()
                .filter(tListEntry -> Objects.nonNull(tListEntry.getKey()))
                .forEach(tListEntry -> map.put(tListEntry.getKey().toString(), tListEntry.getValue()));
        return map;
    }

    public String getParamString(){
        List<String> keyValueList = params.entrySet().stream()
                .sorted(Comparator.comparing(tListEntry -> tListEntry.getKey().toString()))
                .filter(tListEntry -> Objects.nonNull(tListEntry.getKey()))
                .filter(tListEntry -> Objects.nonNull(tListEntry.getValue()))
                .map(tListEntry -> tListEntry.getKey() + "=" + commaDelimit(tListEntry.getValue()))
                .toList();
        if (CollectionUtils.isEmpty(keyValueList)){
            return "";
        }
        StringBuilder sb = new StringBuilder("?").append(keyValueList.get(0));
        if (keyValueList.size() > 1){
            IntStream.range(1, keyValueList.size())
                    .forEach(i -> sb.append("&").append(keyValueList.get(i)));
        }
        return sb.toString();
    }

    private Object commaDelimit(List<String> value) {
        if (Objects.isNull(value)) {
            return null;
        }
        if (value.isEmpty()) {
            return "";
        }
        return value.stream()
                .sorted()
                .collect(Collectors.joining(","));
    }

    public static class QueryParamsBuilder<T>{
        private final Map<T, List<String>> params;

        public QueryParamsBuilder() {
            params = new HashMap<>();
        }
        public QueryParamsBuilder<T> param(T key, String value){
            this.params.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
            return this;
        }
        public QueryParamsBuilder<T> updateParam(T key, String newValue){
            this.params.put(key, Collections.singletonList(newValue));
            return this;
        }
        public QueryParams<T> build(){
            return new QueryParams<T>(params);
        }
    }
}
