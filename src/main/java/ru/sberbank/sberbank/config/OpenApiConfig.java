package ru.sberbank.sberbank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * @author Ramzes Zhdanov
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                    .title("Sberbank account API")
                    .description("Sberbank account OpenAPI Specification")
                    .version("v1.0.0")
                    );
    }
}













