package com.garkclub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Set the URL pattern of your API.addMapping("/api/**")  // Set the URL pattern of your API
                .allowedHeaders("*")
               .allowedOrigins("http://localhost:3000")  // Allow requests from this origin
                .allowedOrigins("http://vps-8cbd5804.vps.ovh.net")  // Allow requests from this origin
                .allowedOrigins("*")  // Allow requests from this origin
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allow these HTTP methods
                .allowCredentials(false)// Allow sending cookies from the client
                .maxAge(3600);
    }
}