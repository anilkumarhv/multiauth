package com.anil.multiauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain httpSecurity(HttpSecurity http) throws Exception {
        http.csrf().and().cors().disable()
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/**", "/login", "authenticate","/authentication-urls").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login()
                .loginPage("/authentication-urls")
                .userInfoEndpoint();
        return http.build();
    }
}
