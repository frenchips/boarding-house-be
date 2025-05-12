package com.adk.kost.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;


@Configuration
public class PropertiesConfig {

    @Autowired
    private Environment envi;


    @Bean(name = "sendMail")
    public String sendMail(){
        return envi.getProperty("spring.mail.username");
    }

    @Bean(name = "waToken")
    public String waToken(){
        return envi.getProperty("whatsapp.access.token");
    }

//    @Bean(name = "secretKey")
//    public String secretKey(){
//        return envi.getProperty("whatsapp.access.secret_key");
//    }

    @Bean(name = "apiWaHost")
    public String apiWaHost(){
        return envi.getProperty("whatsapp.api.url");
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }



}
