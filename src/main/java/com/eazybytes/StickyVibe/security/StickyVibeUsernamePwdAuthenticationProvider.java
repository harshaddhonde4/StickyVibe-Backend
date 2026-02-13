package com.eazybytes.StickyVibe.security;

import com.eazybytes.StickyVibe.entity.Customer;
import com.eazybytes.StickyVibe.entity.Role;
import com.eazybytes.StickyVibe.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Profile("prod")
@Component
@RequiredArgsConstructor
public class StickyVibeUsernamePwdAuthenticationProvider implements AuthenticationProvider
{
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
    Customer customer =
        customerRepository
            .findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found for the username" + email));
    Set<Role> roles = customer.getRoles();
    List<GrantedAuthority> authorities = roles.stream().map(role -> (GrantedAuthority) () -> role.getName()).toList();
    if(passwordEncoder.matches(password, customer.getPasswordHash()))
    {
       return new UsernamePasswordAuthenticationToken(customer, null, authorities);
    }
    else
    {
        throw new BadCredentialsException("Invalid password");
    }
    }
}






