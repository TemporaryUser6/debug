package com.test.attempt1.api.rest.docs;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *   swagger self-documentation init
 *   run mvn spring-boot:run -Drun.arguments="spring.profiles.active=test"
 *   to see a doc at  <a href="http://localhost:8090/swagger-ui/index.html">...</a>
 */
@Configuration
@ComponentScan("com.test.attempt1.api.rest")
public class SwaggerConfig {

    private static final String DOC_TITLE1 = "Test Task API";
    private static final String DOC_TITLE2 = "CryptoHelper test task application";
    private static final String DOC_API_VERSION = "1.0";
    private static final String DOC_AS_JSON_HREF = "DocAsJson";
    private static final String DOC_SWAGGER_HEADER_COMBOBOX_CONTENT =
            "CryptoHelper API NAME definition selectable";

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group(DOC_AS_JSON_HREF)
                .displayName(DOC_SWAGGER_HEADER_COMBOBOX_CONTENT)
                .packagesToScan("com.test.attempt1.api.rest")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(DOC_TITLE1)
                        .description(DOC_TITLE2)
                        .version(DOC_API_VERSION)
                        .license(new License().name("Apache 2.0")
                        .url("http://springdoc.org")))
                        .externalDocs(new ExternalDocumentation()
                        .description("SpringShop Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"));
    }
}
