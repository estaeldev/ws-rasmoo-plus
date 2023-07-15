package com.client.ws.rasmooplus.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
    
    @Bean
    OpenAPI api() {
        return new OpenAPI().info(apiInfo());
    }

    private Info apiInfo() {
        License license = new License();
        license.setName("Rasmoo cursos de tecnologia");

        Info info = new Info();
        info.setTitle("Rasmoo Plus");
        info.setDescription("Api para atender o client Rasmoo Plus");
        info.version("0.0.1");
        info.license(license);

        return info;
    
    }

}
