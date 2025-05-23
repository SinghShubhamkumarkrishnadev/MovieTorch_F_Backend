package com.movieTorch.movieTorch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class MovieDetail {
    @JsonProperty("adult")
    private boolean adult;
    
    @JsonProperty("backdrop_path")
    private String backdrop_path;
    
    @JsonProperty("belongs_to_collection")
    private Object belongs_to_collection;
    
    @JsonProperty("budget")
    private long budget;
    
    @JsonProperty("genres")
    private List<Genre> genres;
    
    @JsonProperty("homepage")
    private String homepage;
    
    @JsonProperty("id")
    private int id;
    
    @JsonProperty("imdb_id")
    private String imdb_id;
    
    @JsonProperty("original_language")
    private String original_language;
    
    @JsonProperty("original_title")
    private String original_title;
    
    @JsonProperty("overview")
    private String overview;
    
    @JsonProperty("popularity")
    private double popularity;
    
    @JsonProperty("poster_path")
    private String poster_path;
    
    @JsonProperty("production_companies")
    private List<ProductionCompany> production_companies;
    
    @JsonProperty("production_countries")
    private List<ProductionCountry> production_countries;
    
    @JsonProperty("release_date")
    private String release_date;
    
    @JsonProperty("revenue")
    private long revenue;
    
    @JsonProperty("runtime")
    private int runtime;
    
    @JsonProperty("spoken_languages")
    private List<SpokenLanguage> spoken_languages;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("tagline")
    private String tagline;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("video")
    private boolean video;
    
    @JsonProperty("vote_average")
    private double vote_average;
    
    @JsonProperty("vote_count")
    private int vote_count;
}

@Data
class Genre {
    @JsonProperty("id")
    private int id;
    
    @JsonProperty("name")
    private String name;
}

@Data
class ProductionCompany {
    @JsonProperty("id")
    private int id;
    
    @JsonProperty("logo_path")
    private String logo_path;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("origin_country")
    private String origin_country;
}

@Data
class ProductionCountry {
    @JsonProperty("iso_3166_1")
    private String iso_3166_1;
    
    @JsonProperty("name")
    private String name;
}

@Data
class SpokenLanguage {
    @JsonProperty("english_name")
    private String english_name;
    
    @JsonProperty("iso_639_1")
    private String iso_639_1;
    
    @JsonProperty("name")
    private String name;
}
