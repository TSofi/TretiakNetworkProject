package com.tretiak.project.tretiaknetworkproject.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfiguration {


    // Default CORS configuration for all endpoints
    @Bean
    public WebMvcConfigurer corseConfigurer(){
        return new WebMvcConfigurer(){
            @Override
            public void addCorsMappings(CorsRegistry registry){
                registry.addMapping("/**").allowedMethods("*").allowedOrigins("*");
            }
        };
    }

    // CORS configuration for specific endpoints /auth/login
    @Bean
    public WebMvcConfigurer specificCorsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Allow CORS for specific endpoint(s), e.g., "/auth/login"
                registry.addMapping("/auth/login")
                        .allowedMethods("POST")
                        .allowedOrigins("http://localhost:3000");
            }
        };
    }
}