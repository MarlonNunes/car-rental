package com.marlonnunes.carrental.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public GroupedOpenApi openApi(){
        return GroupedOpenApi.builder()
                .group("spring-doc")
                .packagesToScan("com.marlonnunes.carrental.controller")
                .build();
    }
}
