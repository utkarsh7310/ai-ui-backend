package com.ai.uibuilder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UiComponent {
    private String type;
    private String content;
    private String tailwindClass;
    private String animationType;
    private Map<String, Object> extraProps; // O capital hona chahiye!
}