package com.rogerfitness.workoutsystem.responses;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.rogerfitness.workoutsystem.exceptions.Error;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {
    @Singular
    private List<Error> errors;
    @Setter(AccessLevel.NONE)
    private Map<String, String> properties;
    @JsonAnyGetter
    public Map<String, String> getProperties(){return properties;}
    @JsonAnySetter
    public void addProperty(String key, String value){
        if (Objects.isNull(this.properties)){
            this.properties = new HashMap<>();
        }
        this.properties.put(key, value);
    }
}
