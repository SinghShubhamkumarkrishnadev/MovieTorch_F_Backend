package com.movieTorch.movieTorch.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MovieTorch API")
                        .version("1.0")
                        .description("API for accessing movie information from TMDB. This API provides endpoints to search movies, " +
                                "get movie details, recommendations, credits, images, and more.")
                        .termsOfService("https://www.themoviedb.org/terms-of-use")
                        .contact(new Contact()
                                .name("MovieTorch Support")
                                .email("support@movietorch.com")
                                .url("https://www.movietorch.com/support"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("https://api.movietorch.com").description("Production Server"),
                        new Server().url("https://staging-api.movietorch.com").description("Staging Server"),
                        new Server().url("http://localhost:8080").description("Development Server")
                ));
    }
}
