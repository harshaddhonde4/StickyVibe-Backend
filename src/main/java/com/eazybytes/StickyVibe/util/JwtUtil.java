package com.eazybytes.StickyVibe.util;

import com.eazybytes.StickyVibe.constants.ApplicationConstants;
import com.eazybytes.StickyVibe.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
@RequiredArgsConstructor
public class JwtUtil
{
    private final Environment env;

    public String generateJwtToken(Authentication authentication)
    {
        String jwt = "";
        String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY, ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        Customer fetchedCustomer = (Customer) authentication.getPrincipal();
        jwt = Jwts.builder()
                .issuer("StickyVibe")
                .claim("username", fetchedCustomer.getEmail())
                .claim("email", fetchedCustomer.getEmail())
                .claim("phone", fetchedCustomer.getMobileNumber())
                .claim("roles", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(java.util.stream.Collectors.joining(",")))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000 * 10))
                .signWith(secretKey)
                .compact();
        return jwt;
    }
}

