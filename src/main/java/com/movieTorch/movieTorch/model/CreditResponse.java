package com.movieTorch.movieTorch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class CreditResponse {
    @JsonProperty("id")
    private int id;
    
    @JsonProperty("cast")
    private List<Cast> cast;
    
    @JsonProperty("crew")
    private List<Crew> crew;
}
