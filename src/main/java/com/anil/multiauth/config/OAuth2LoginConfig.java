package com.anil.multiauth.config;

import com.anil.multiauth.strategy.ClientRegistrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Configuration
public class OAuth2LoginConfig {
    private final Map<String, ClientRegistrationStrategy> registrationStrategies;

    public OAuth2LoginConfig(Map<String, ClientRegistrationStrategy> registrationStrategies) {
        this.registrationStrategies = registrationStrategies;
    }

    @Bean
    ClientRegistrationRepository clientRegistrationRepository() {
        List<AuthProvider> authProviders = Arrays.asList(AuthProvider.keycloak);

        List<ClientRegistration> registrations = authProviders.stream()
                .map(p -> registrationStrategies.get(p.name()).clientRegistration())
                .toList();
        return new InMemoryClientRegistrationRepository(registrations);
    }
}
