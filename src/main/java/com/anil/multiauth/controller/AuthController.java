package com.anil.multiauth.controller;

import com.anil.multiauth.service.TokenProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class AuthController {

    @Value("${client.target.url}")
    String targetLocation;

    @Autowired
    private TokenProviderService tokenProviderService;

    @GetMapping("/")
    public RedirectView OAuthLogin(@AuthenticationPrincipal OidcUser user, RedirectAttributes attributes) {
        String emailOrName = user.getAttribute("email") != null ? user.getAttribute("email") : user.getName();

//        String token = tokenProviderService.createJwtToken(emailOrName, "oauth2", user.getAttributes());

        attributes.addAttribute("token", user.getIdToken().getTokenValue());
        System.out.println(user.getIdToken().getTokenValue());
        return new RedirectView(targetLocation);
    }
}
