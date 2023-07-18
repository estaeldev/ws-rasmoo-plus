package com.client.ws.rasmooplus.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    
    public static final String AUTENTICACAO = "Authentication";
    public static final String PAYMENT_INFO = "PaymentInfo";
    public static final String SUBSCRIPTION_TYPE = "SubscriptionType";
    public static final String USER = "User";
    public static final String USER_TYPE = "UserType";

    @Bean
    OpenAPI api() {

        SecurityRequirement security = new SecurityRequirement();
        security.addList("bearerAuth");
        
        return new OpenAPI()
            .info(apiInfo())
            .tags(tags())
            .security(Arrays.asList(security));
        
    }

    private Info apiInfo() {
        License license = new License();
        license.setName("Rasmoo cursos de tecnologia");

        Info info = new Info();
        info.setTitle("Rasmoo Plus");
        info.setDescription("Api para atender o client Rasmoo Plus");
        info.version("1.0.0");
        info.license(license);

        return info;
    
    }

    private List<Tag> tags() {
        List<Tag> tagList = new ArrayList<>();

        Tag userType = new Tag();
        userType.setName(USER_TYPE);
        userType.setDescription("Operações onde é escolhido o perfil do usuario na plataforma");

        Tag user = new Tag();
        user.setName(USER);
        user.setDescription("Operações responsaveis pela criação e informaçoes do usuario");

        Tag subscriptionType = new Tag();
        subscriptionType.setName(SUBSCRIPTION_TYPE);
        subscriptionType.setDescription("Operações onde é escolhido o tipo de plano na plataforma");

        Tag paymentInfo = new Tag();
        paymentInfo.setName(PAYMENT_INFO);
        paymentInfo.setDescription("Operações responsaveis pelo processamento do pagamento do usuario");

        Tag authentication = new Tag();
        authentication.setName(AUTENTICACAO);
        authentication.setDescription("Operações responsaveis pela validação e acesso do usuario");

        tagList.add(userType);
        tagList.add(user);
        tagList.add(subscriptionType);
        tagList.add(paymentInfo);
        tagList.add(authentication);

        return tagList;
    }

}
