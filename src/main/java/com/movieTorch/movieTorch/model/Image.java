package com.movieTorch.movieTorch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Image {
    @JsonProperty("aspect_ratio")
    private double aspectRatio;
    
    @JsonProperty("height")
    private int height;
    
    @JsonProperty("iso_639_1")
    private String iso6391;
    
    @JsonProperty("file_path")
    private String filePath;
    
    @JsonProperty("vote_average")
    private double voteAverage;
    
    @JsonProperty("vote_count")
    private int voteCount;
    
    @JsonProperty("width")
    private int width;
}
