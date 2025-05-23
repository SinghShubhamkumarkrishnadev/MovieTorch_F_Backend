package com.movieTorch.movieTorch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class UpcomingMovieResponse {
    @JsonProperty("dates")
    private Dates dates;
    
    @JsonProperty("page")
    private int page;
    
    @JsonProperty("results")
    private List<Movie> results;
    
    @JsonProperty("total_pages")
    private int totalPages;
    
    @JsonProperty("total_results")
    private int totalResults;
}
