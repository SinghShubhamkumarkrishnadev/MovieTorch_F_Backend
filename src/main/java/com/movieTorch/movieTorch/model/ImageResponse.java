package com.movieTorch.movieTorch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class ImageResponse {
    @JsonProperty("id")
    private int id;
    
    @JsonProperty("backdrops")
    private List<Image> backdrops;
    
    @JsonProperty("posters")
    private List<Image> posters;
}
