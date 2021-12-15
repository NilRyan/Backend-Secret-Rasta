package com.rastatech.secretrasta.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rastatech.secretrasta.model.Role;
import com.rastatech.secretrasta.model.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtilities {

    private String jwtSecret;
    private Algorithm algorithm;

    public JwtUtilities(@Value("${jwt.signing.secret}") String jwtSecret) {
        this.jwtSecret = jwtSecret;
        this.algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
    }

    public String buildAccessJwt(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10080 * 60 * 1000))
                .withIssuer("RastaMan in collaboration with Nil pogi")
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public String buildAccessJwt(UserEntity user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10080 * 60 * 1000))
                .withIssuer("RastaMan in collaboration with Nil pogi")
                .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public String buildRefreshJwt(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 43800 * 60 * 1000))
                .withIssuer("RastaMan in collaboration with Nil pogi")
                .sign(algorithm);
    }

    public DecodedJWT decodeJWT(String authorizationHeader) {
        String token = authorizationHeader.substring("Bearer ".length());
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }
}
