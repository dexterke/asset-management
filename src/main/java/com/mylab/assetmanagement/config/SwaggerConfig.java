package com.mylab.assetmanagement.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    /*
     * Build springdoc.api-docs
     * http://localhost:8080/api-docs/
     */
    public OpenAPI applicationAPI() {
        return new OpenAPI()
                .info(new Info().title("Asset-management RestAPI")
                        .description("Asset Management Demo App")
                        .version("v0.0.X")
                        .license(new License().name("springdoc-openapi").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Asset-management Wiki Documentation")
                        .url("https://asset-management.wiki.github.org/docs"));

    }

    /*
     * Build springdoc.swagger-ui
     * http://localhost:8080/swagger-ui/index.html
     */
    @Bean
    public GroupedOpenApi applicationGroupAPI() {
        return GroupedOpenApi.builder()
                .group("asset-management")
                .packagesToScan("com.mylab.assetmanagement.controller")
                .pathsToMatch("/api/v1/**")
                .build();
    }
}
