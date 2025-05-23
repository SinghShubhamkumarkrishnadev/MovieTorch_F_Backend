package com.movieTorch.movieTorch.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {
    
    @Value("${tmdb.api.base-url}")
    private String tmdbBaseUrl;
    
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        // Configure HTTP client with timeouts
        HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 15000) // 10 seconds connection timeout
            .responseTimeout(Duration.ofSeconds(15)) // 10 seconds response timeout
            .doOnConnected(conn -> 
                conn.addHandlerLast(new ReadTimeoutHandler(15, TimeUnit.SECONDS))
                    .addHandlerLast(new WriteTimeoutHandler(15, TimeUnit.SECONDS)));
        
        // Configure memory allocation for responses
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)) // 16MB buffer
            .build();
        
        return builder
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .exchangeStrategies(exchangeStrategies)
            .baseUrl(tmdbBaseUrl)
            .build();
    }
}