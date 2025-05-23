package com.movieTorch.movieTorch.service;

import com.movieTorch.movieTorch.exception.MovieServiceException;
import com.movieTorch.movieTorch.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.net.SocketException;
import java.time.Duration;

@Service
public class TMDBService {
	private final WebClient webClient;
	private final CacheManager cacheManager;

	@Value("${tmdb.api.key}")
	private String apiKey;

	TMDBService(WebClient webClient, CacheManager cacheManager) {
		this.webClient = webClient;
		this.cacheManager = cacheManager;
	}

	// Helper method to handle common error mapping
	private <T> Mono<T> handleErrors(Mono<T> monoResponse, String errorMessage) {
		return monoResponse
				.retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
						.filter(throwable -> throwable instanceof SocketException))
				.onErrorMap(WebClientResponseException.class, e ->
						MovieServiceException.apiError("Status " + e.getStatusCode().value() +
								" - " + e.getStatusText()))
				.onErrorMap(e -> !(e instanceof MovieServiceException),
						e -> MovieServiceException.serverError(errorMessage, e));
	}

	// Helper method for caching
	private <T> Mono<T> cacheResult(String cacheName, Object key, Mono<T> dataFetcher) {
		Cache cache = cacheManager.getCache(cacheName);
		if (cache != null) {
			Cache.ValueWrapper valueWrapper = cache.get(key);
			if (valueWrapper != null) {
				return Mono.justOrEmpty((T) valueWrapper.get());
			} else {
				return dataFetcher.doOnNext(result -> cache.put(key, result));
			}
		}
		return dataFetcher;
	}

	public Mono<MovieResponse> getPopularMovies(int page) {
		return cacheResult("popularMovies", page,
				handleErrors(
						webClient.get()
								.uri(uriBuilder -> uriBuilder
										.path("/movie/popular")
										.queryParam("api_key", apiKey)
										.queryParam("page", page)
										.build())
								.retrieve()
								.bodyToMono(MovieResponse.class),
						"Error fetching popular movies"
				)
		);
	}

	public Mono<MovieResponse> searchMovies(String query, int page) {
		String cacheKey = query + "_" + page;
		return cacheResult("searchResults", cacheKey,
				handleErrors(
						webClient.get()
								.uri(uriBuilder -> uriBuilder
										.path("/search/movie")
										.queryParam("api_key", apiKey)
										.queryParam("query", query)
										.queryParam("page", page)
										.build())
								.retrieve()
								.bodyToMono(MovieResponse.class),
						"Error searching movies"
				)
		);
	}

	public Mono<MovieDetail> getMovieDetails(int movieId) {
		return cacheResult("movieDetails", movieId,
				handleErrors(
						webClient.get()
								.uri(uriBuilder -> uriBuilder
										.path("/movie/" + movieId)
										.queryParam("api_key", apiKey)
										.build())
								.retrieve()
								.bodyToMono(MovieDetail.class),
						"Error fetching movie details for ID: " + movieId
				)
		);
	}

	public Mono<MovieResponse> getRecommendation(int movieId, int page) {
		String cacheKey = movieId + "_" + page;
		return cacheResult("recommendations", cacheKey,
				handleErrors(
						webClient.get()
								.uri(uriBuilder -> uriBuilder
										.path("/movie/" + movieId + "/recommendations")
										.queryParam("api_key", apiKey)
										.queryParam("page", page)
										.build())
								.retrieve()
								.bodyToMono(MovieResponse.class),
						"Error fetching movie recommendations for ID: " + movieId
				)
		);
	}

	public Mono<CreditResponse> getCreditsResponse(int id) {
		return cacheResult("credits", id,
				handleErrors(
						webClient.get()
								.uri(uriBuilder -> uriBuilder
										.path("/movie/" + id + "/credits")
										.queryParam("api_key", apiKey)
										.build())
								.retrieve()
								.bodyToMono(CreditResponse.class),
						"Error fetching movie credits for ID: " + id
				)
		);
	}

	public Mono<VideoResponse> getVideoResponse(int id) {
		return cacheResult("videos", id,
				handleErrors(
						webClient.get()
								.uri(uriBuilder -> uriBuilder
										.path("/movie/" + id + "/videos")
										.queryParam("api_key", apiKey)
										.build())
								.retrieve()
								.bodyToMono(VideoResponse.class),
						"Error fetching movie videos for ID: " + id
				)
		);
	}

	public Mono<ImageResponse> getImageResponse(int id) {
		return cacheResult("images", id,
				handleErrors(
						webClient.get()
								.uri(uriBuilder -> uriBuilder
										.path("/movie/" + id + "/images")
										.queryParam("api_key", apiKey)
										.build())
								.retrieve()
								.bodyToMono(ImageResponse.class),
						"Error fetching movie images for ID: " + id
				)
		);
	}

	public Mono<MovieResponse> getSimilarResponse(int id, int page) {
		String cacheKey = id + "_" + page;
		return cacheResult("similar", cacheKey,
				handleErrors(
						webClient.get()
								.uri(uriBuilder -> uriBuilder
										.path("/movie/" + id + "/similar")
										.queryParam("api_key", apiKey)
										.queryParam("page", page)
										.build())
								.retrieve()
								.bodyToMono(MovieResponse.class),
						"Error fetching similar movies for ID: " + id
				)
		);
	}

	public Mono<ReviewResponse> getReviewResponse(int id, int page) {
		String cacheKey = id + "_" + page;
		return cacheResult("reviews", cacheKey,
				handleErrors(
						webClient.get()
								.uri(uriBuilder -> uriBuilder
										.path("/movie/" + id + "/reviews")
										.queryParam("api_key", apiKey)
										.queryParam("page", page)
										.build())
								.retrieve()
								.bodyToMono(ReviewResponse.class),
						"Error fetching movie reviews for ID: " + id
				)
		);
	}

	public Mono<KeywordResponse> getKeywordResponse(int id) {
		return cacheResult("keywords", id,
				handleErrors(
						webClient.get()
								.uri(uriBuilder -> uriBuilder
										.path("/movie/" + id + "/keywords")
										.queryParam("api_key", apiKey)
										.build())
								.retrieve()
								.bodyToMono(KeywordResponse.class),
						"Error fetching movie keywords for ID: " + id
				)
		);
	}

	public Mono<MovieResponse> getTopRatedResponse(int page) {
		return cacheResult("topRated", page,
				handleErrors(
						webClient.get()
								.uri(uriBuilder -> uriBuilder
										.path("/movie/top_rated")
										.queryParam("api_key", apiKey)
										.queryParam("page", page)
										.build())
								.retrieve()
								.bodyToMono(MovieResponse.class),
						"Error fetching top rated movies"
				)
		);
	}

	public Mono<UpcomingMovieResponse> getUpcomingResponse(int page) {
		return cacheResult("upcoming", page,
				handleErrors(
						webClient.get()
								.uri(uriBuilder -> uriBuilder
										.path("/movie/upcoming")
										.queryParam("api_key", apiKey)
										.queryParam("page", page)
										.build())
								.retrieve()
								.bodyToMono(UpcomingMovieResponse.class),
						"Error fetching upcoming movies"
				)
		);
	}

	public Mono<NowPlayingMovieResponse> getNowPlayingResponse(int page) {
		return cacheResult("nowPlaying", page,
				handleErrors(
						webClient.get()
								.uri(uriBuilder -> uriBuilder
										.path("/movie/now_playing")
										.queryParam("api_key", apiKey)
										.queryParam("page", page)
										.build())
								.retrieve()
								.bodyToMono(NowPlayingMovieResponse.class),
						"Error fetching now playing movies"
				)
		);
	}
}
