package com.movieTorch.movieTorch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Keyword {
    @JsonProperty("id")
    private int id;
    
    @JsonProperty("name")
    private String name;
}
