package com.anil.multiauth.service;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Service
public class TokenProviderService {

    private final String tokenSecret;


    public TokenProviderService(@Value("${jwt.token.secret}") String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    public String createJwtToken(String userName, String authType, Map<String, Object> attributes) {

        String firstName = attributes != null ? (String) attributes.get("firstName") : "";
        String lastName = attributes != null ? (String) attributes.get("lastName") : "";

        Date currentDate = new Date();
        LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(1);

        Date expires = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        String encodedKey = encode(tokenSecret);

        JwtBuilder builder = Jwts.builder().claim("authType",authType)
                .claim("firstName",firstName).claim("lastName",lastName)
                .claim("email",userName).setId(userName)
                .setIssuedAt(currentDate)
                .setSubject(userName)
                .signWith(signatureAlgorithm,encodedKey)
                .setExpiration(expires);
        return builder.compact();
    }

    private String encode(String decodedStr) {
        return Base64.getEncoder().encodeToString(decodedStr.getBytes());
    }
}
