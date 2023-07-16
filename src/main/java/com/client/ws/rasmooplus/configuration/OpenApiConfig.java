package com.client.ws.rasmooplus.configuration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
@SecurityScheme(
    name = "bearerAuth",
    description = "JWT auth description",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
    
    @Bean
    OpenAPI api() {
        Tag userType = new Tag();
        userType.setName("UserType");
        userType.setDescription("Endpoit para UserType");

        Tag user = new Tag();
        user.setName("User");
        user.setDescription("Endpoit para User");

        Tag subscriptionType = new Tag();
        subscriptionType.setName("SubscriptionType");
        subscriptionType.setDescription("Endpoit para SubsciptionType");

        Tag paymentInfo = new Tag();
        paymentInfo.setName("PaymentInfo");
        paymentInfo.setDescription("Endpoit para PaymentInfo");

        Tag authentication = new Tag();
        authentication.setName("Authentication");
        authentication.setDescription("Endpoit para Authentication");

        SecurityRequirement security = new SecurityRequirement();
        security.addList("bearerAuth");
        
        return new OpenAPI()
            .info(apiInfo())
            .tags(Arrays.asList(userType, user, subscriptionType, paymentInfo, authentication))
            .security(Arrays.asList(security));
        
    }

    private Info apiInfo() {
        License license = new License();
        license.setName("Rasmoo cursos de tecnologia");

        Info info = new Info();
        info.setTitle("Rasmoo Plus");
        info.setDescription("Api para atender o client Rasmoo Plus");
        info.version("1.0");
        info.license(license);

        return info;
    
    }

}
