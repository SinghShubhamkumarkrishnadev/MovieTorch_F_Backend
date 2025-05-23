package com.movieTorch.movieTorch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Crew {
    @JsonProperty("adult")
    private boolean adult;
    
    @JsonProperty("gender")
    private int gender;
    
    @JsonProperty("id")
    private int id;
    
    @JsonProperty("known_for_department")
    private String knownForDepartment;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("original_name")
    private String originalName;
    
    @JsonProperty("popularity")
    private double popularity;
    
    @JsonProperty("profile_path")
    private String profilePath;
    
    @JsonProperty("credit_id")
    private String creditId;
    
    @JsonProperty("department")
    private String department;
    
    @JsonProperty("job")
    private String job;
}
