package com.movieTorch.movieTorch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Video {
    @JsonProperty("iso_639_1")
    private String iso6391;
    
    @JsonProperty("iso_3166_1")
    private String iso31661;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("site")
    private String site;
    
    @JsonProperty("size")
    private int size;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("official")
    private boolean official;
    
    @JsonProperty("published_at")
    private String publishedAt;
    
    @JsonProperty("id")
    private String id;
}
