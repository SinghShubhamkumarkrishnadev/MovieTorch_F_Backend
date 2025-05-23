package com.movieTorch.movieTorch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MovieServiceException extends ResponseStatusException {

    public MovieServiceException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public MovieServiceException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }

    public static MovieServiceException notFound(int movieId) {
        return new MovieServiceException(
                HttpStatus.NOT_FOUND,
                "Movie with ID " + movieId + " not found"
        );
    }

    public static MovieServiceException apiError(String message) {
        return new MovieServiceException(
                HttpStatus.BAD_GATEWAY,
                "TMDB API error: " + message
        );
    }

    public static MovieServiceException serverError(String message, Throwable cause) {
        return new MovieServiceException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Server error: " + message,
                cause
        );
    }
}
