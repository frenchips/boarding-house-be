package com.adk.kost.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {
    @Bean
    public OpenAPI myOpenApi(){

        Contact contact = new Contact();
        contact.setName("NUKOST");

        Info info = new Info().title("API Nukost").contact(contact).version("1.0").description("Nur Kost");

        return new OpenAPI().info(info);
    }
}
