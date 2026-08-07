package com.poseidoncapitalsolutions.poseidon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI poseidonOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Poseidon Capital Solutions Trading API")
                        .description("API documentation for Poseidon Capital Solutions trading platform")
                        .version("1.0.0")
                        .contact(new Contact().name("Poseidon Capital Solutions").email("support@poseidoncapitalsolutions.com"))
                        .license(new License().name("Internal Use Only")));
    }
}