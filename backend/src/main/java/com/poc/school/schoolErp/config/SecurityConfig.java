package com.poc.school.schoolErp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CORS configuration
        http.cors()
            .configurationSource(corsConfigurationSource());  // Enable CORS

        // Disable security for the entire application
        http
            .authorizeRequests()
                .anyRequest().permitAll()  // Allow all requests without authentication
            .and()
            .csrf().disable();  // Disable CSRF protection (recommended for REST APIs)

        return http.build();
    }

    // CORS configuration
    private CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow React app to make requests
        config.addAllowedOrigin("http://localhost:3000");  // Your React app URL
        config.addAllowedMethod("*");  // Allow all HTTP methods (GET, POST, etc.)
        config.addAllowedHeader("*");  // Allow all headers
        config.setAllowCredentials(true);  // Allow credentials (cookies, etc.)

        source.registerCorsConfiguration("/**", config);  // Apply CORS configuration globally

        return source;
    }
}
