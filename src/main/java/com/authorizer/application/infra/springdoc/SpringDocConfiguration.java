/*
 * Responsible for SpringDoc
 */
package com.authorizer.application.infra.springdoc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * @author Aline
 *
 */
@Configuration
public class SpringDocConfiguration {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .components(new Components().addSecuritySchemes("bearer-key",
            new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer")
                .bearerFormat("JWT")))
        .info(new Info().title("Card Authentication API").description("Card Authentication")
            .contact(new Contact().name("Time Backend").email("linesad@gmail.com"))
            .license(new License().name("Apache 2.0").url("http://cardauthentication/api/licenca")));
  }

}
