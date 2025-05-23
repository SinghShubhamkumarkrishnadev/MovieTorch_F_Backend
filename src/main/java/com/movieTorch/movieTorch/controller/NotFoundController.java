package com.movieTorch.movieTorch.controller;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@Order(Integer.MAX_VALUE) // This ensures the controller is evaluated last
public class NotFoundController {

    // Specifically handle non-numeric IDs in the /api/movies/{id} path
    @GetMapping("/api/movies/{id:[^0-9]+}/**")
    public ResponseEntity<Map<String, Object>> handleNonNumericMovieId() {
        return createNotFoundResponse();
    }

    // Handle all other non-matched paths
    @RequestMapping(value = "/**")
    public ResponseEntity<Map<String, Object>> handleNotFound() {
        return createNotFoundResponse();
    }

    private ResponseEntity<Map<String, Object>> createNotFoundResponse() {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now().toString());
        errorResponse.put("status", HttpStatus.NOT_FOUND.value());
        errorResponse.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        errorResponse.put("message", "The requested URL was not found on this server");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
