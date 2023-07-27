package com.rogerfitness.workoutsystem.apis;

import lombok.Data;

@Data
public class InlineImageDTO {
    private String link;
    private String source;
    private String thumbnail;
    private String original;
    private String title;
    private String source_name;
}
