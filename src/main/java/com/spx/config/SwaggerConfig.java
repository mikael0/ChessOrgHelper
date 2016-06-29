package com.spx.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.function.Predicate;

/**
 * Created by timofb on 11/1/2015.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        com.google.common.base.Predicate<String> regex = new com.google.common.base.Predicate<String>() {
            @Override
            public boolean apply(String s) {
                return s.contains("/rest");
            }
        };
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex).build().pathMapping("/")
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Stroy Project X REST API",
                "Description of our API",
                "API TOS",
                "bogdan.timofeev@arcanone.com",
                "API License",
                "API License URL",
                "login"
        );
        return apiInfo;
    }
}