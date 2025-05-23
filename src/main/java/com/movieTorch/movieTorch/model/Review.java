package com.movieTorch.movieTorch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Review {
    @JsonProperty("author")
    private String author;
    
    @JsonProperty("author_details")
    private AuthorDetails authorDetails;
    
    @JsonProperty("content")
    private String content;
    
    @JsonProperty("created_at")
    private String createdAt;
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("updated_at")
    private String updatedAt;
    
    @JsonProperty("url")
    private String url;
}
