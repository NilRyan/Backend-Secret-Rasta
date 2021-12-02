package com.rastatech.secretrasta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .apis(RequestHandlerSelectors.basePackage("com.rastatech"))
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails() {
        return new ApiInfo(
                "Secret Rasta API",
                "All APIs for Secret Rasta mobile team",
                "1.0",
                "Boss Nil Pogi",
                new springfox.documentation.service.Contact("Neil Ryan Lipa-od", "neil.lipa-od@whitecloak.com", "facebook.com"),
                "API License",
                "facebook.com",
                Collections.emptyList());
    }
}
