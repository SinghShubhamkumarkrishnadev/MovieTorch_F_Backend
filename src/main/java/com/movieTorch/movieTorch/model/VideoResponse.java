package com.movieTorch.movieTorch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class VideoResponse {
    @JsonProperty("id")
    private int id;
    
    @JsonProperty("results")
    private List<Video> results;
}
