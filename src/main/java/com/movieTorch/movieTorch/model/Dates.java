package com.movieTorch.movieTorch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Dates {
    @JsonProperty("maximum")
    private String maximum;
    
    @JsonProperty("minimum")
    private String minimum;
}
