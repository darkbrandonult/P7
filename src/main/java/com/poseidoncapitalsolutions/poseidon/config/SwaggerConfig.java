package com.poseidoncapitalsolutions.poseidon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI poseidonOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Poseidon Capital Solutions Trading API")
                        .description("API documentation for Poseidon Capital Solutions trading platform")
                        .version("1.0.0"));
    }
}