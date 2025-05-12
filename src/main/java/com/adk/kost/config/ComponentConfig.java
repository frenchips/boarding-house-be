package com.adk.kost.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.adk.kost.common", "com.adk.kost.config", "com.adk.kost.controller",
        "com.adk.kost.service"})
public class ComponentConfig {
}
