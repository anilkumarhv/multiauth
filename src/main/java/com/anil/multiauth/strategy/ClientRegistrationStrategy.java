package com.anil.multiauth.strategy;

import org.springframework.security.oauth2.client.registration.ClientRegistration;

public interface ClientRegistrationStrategy {

    public default ClientRegistration clientRegistration(){
        return null;
    }

    public String getLoginUrl();
}
