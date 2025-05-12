package com.adk.kost.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = "com.adk.kost.entity")
public class ScanConfig {
}
