package com.movieTorch.movieTorch.controller;

import com.movieTorch.movieTorch.service.*;
import com.movieTorch.movieTorch.model.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/movies", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovieController {

    private final TMDBService tmdbService;
    MovieController(TMDBService tmdbService){
        this.tmdbService = tmdbService;
    }


    @GetMapping(value = "/popular", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<MovieResponse> getPopularMovies(@RequestParam(defaultValue = "1") int page) {
        return tmdbService.getPopularMovies(page);
    }
    
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<MovieDetail> getMovieDetails(@PathVariable int id) {
        return tmdbService.getMovieDetails(id);
    }
    
    @GetMapping(value = "/search")
    public Mono<MovieResponse> searchMovies(@RequestParam String query, @RequestParam(defaultValue = "1") int page){
    	return tmdbService.searchMovies(query, page);
    }
    
    @GetMapping(value = "/top_rated", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<MovieResponse> getTopRatedResponse(@RequestParam(defaultValue = "1") int page){
    	return tmdbService.getTopRatedResponse(page);
    }

    @GetMapping(value = "/upcoming", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<UpcomingMovieResponse> getUpcomingResponse(@RequestParam(defaultValue = "1") int page){
        return tmdbService.getUpcomingResponse(page);
    }

    @GetMapping(value = "/now_playing", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<NowPlayingMovieResponse> getNowPlayingResponse(@RequestParam(defaultValue = "1") int page){
        return tmdbService.getNowPlayingResponse(page);
    }

    @GetMapping(value = "/{id}/recommendations", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<MovieResponse> getRecommendation(@PathVariable int id,
                                                 @RequestParam(defaultValue = "1") int page) {
        return tmdbService.getRecommendation(id, page);
    }
    @GetMapping(value = "/{id}/credits", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CreditResponse> getCreditsResponse(@PathVariable int id){
    	return tmdbService.getCreditsResponse(id);
    }
    
    @GetMapping(value = "/{id}/images", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ImageResponse> getImageResponse(@PathVariable int id){
    	return tmdbService.getImageResponse(id);
    }

    @GetMapping(value = "/{id}/similar", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<MovieResponse> getSimilarResponse(@PathVariable int id,
                                                  @RequestParam(defaultValue = "1") int page){
        return tmdbService.getSimilarResponse(id, page);
    }

    @GetMapping(value = "/{id}/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ReviewResponse> getReviewResponse(@PathVariable int id,
                                                  @RequestParam(defaultValue = "1") int page){
        return tmdbService.getReviewResponse(id, page);
    }
    @GetMapping(value = "/{id}/keywords", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<KeywordResponse> getKeywordResponse(@PathVariable int id){
    	return tmdbService.getKeywordResponse(id);
    } 
    
    @GetMapping(value = "/{id}/videos", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<VideoResponse> getVideoResponse(@PathVariable int id){
        return tmdbService.getVideoResponse(id);
    }


    @GetMapping(value = "/latest", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<MovieDetail> getLatestResponse() {
        return tmdbService.getLatestResponse();
    }


    @GetMapping(value = "/trending", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<MovieResponse> getTrendingResponse(@RequestParam(defaultValue = "day") String timeWindow,
                                                   @RequestParam(defaultValue = "1") int page) {
        return tmdbService.getTrendingResponse(timeWindow, page);
    }

    @GetMapping(value = "/genres", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<GenreListResponse> getGenreList() {
        return tmdbService.getGenreList();
    }

    @GetMapping(value = "/genre", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<MovieResponse> getMoviesByGenre(@RequestParam int genreId,
                                                @RequestParam(defaultValue = "1") int page) {
        return tmdbService.getMoviesByGenre(genreId, page);
    }

}
