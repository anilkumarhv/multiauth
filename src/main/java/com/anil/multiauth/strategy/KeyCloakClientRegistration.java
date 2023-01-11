package com.anil.multiauth.strategy;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.stereotype.Component;

@Component("keycloak")
public class KeyCloakClientRegistration implements ClientRegistrationStrategy {

    private static final String authorizationRequestBaseUri = "/oauth2/authorization/keycloak";

    @Override
    public ClientRegistration clientRegistration() {
        return ClientRegistration.withRegistrationId("keycloak")
                .clientId("springboot-multiauth")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("openid", "profile", "email")
                .issuerUri("http://localhost:8081/realms/anil")
                .authorizationUri("http://localhost:8081/realms/anil/protocol/openid-connect/auth")
                .tokenUri("http://localhost:8081/realms/anil/protocol/openid-connect/token")
                .userInfoUri("http://localhost:8081/realms/anil/protocol/openid-connect/userinfo")
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .jwkSetUri("http://localhost:8081/realms/anil/protocol/openid-connect/certs")
                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                .clientName("keycloak")
                .build();
    }

    @Override
    public String getLoginUrl() {
        return authorizationRequestBaseUri;
    }
}
