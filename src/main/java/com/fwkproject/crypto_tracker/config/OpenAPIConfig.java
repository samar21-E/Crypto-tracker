package com.fwkproject.crypto_tracker.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI cryptoTrackerApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Crypto Tracker API")
                        .description("API documentation for tracked coins, alerts, and price monitoring")
                        .version("1.0.0")
                );
    }
}
