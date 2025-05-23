package com.movieTorch.movieTorch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class ReviewResponse {
    @JsonProperty("id")
    private int id;
    
    @JsonProperty("page")
    private int page;
    
    @JsonProperty("results")
    private List<Review> results;
    
    @JsonProperty("total_pages")
    private int totalPages;
    
    @JsonProperty("total_results")
    private int totalResults;
}
