package com.movieTorch.movieTorch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class KeywordResponse {
    @JsonProperty("id")
    private int id;
    
    @JsonProperty("keywords")
    private List<Keyword> keywords;
}
