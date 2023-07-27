package com.rogerfitness.workoutsystem.apis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleInlineImagesResponse {
    private List<InlineImageDTO> inline_images;
}
