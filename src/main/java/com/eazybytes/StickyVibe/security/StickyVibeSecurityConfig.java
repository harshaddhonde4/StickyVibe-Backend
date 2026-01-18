package com.eazybytes.StickyVibe.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class StickyVibeSecurityConfig {
    private final List<String> publicPaths;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrfConfig -> csrfConfig.disable())
                .cors(corsConfig -> corsConfig.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests((requests) -> {
                    publicPaths.forEach(path ->
                            requests.requestMatchers(path).permitAll());
                    requests.anyRequest().authenticated();
                }
        )
                .formLogin(withDefaults())
                .httpBasic(withDefaults()).build();

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource()
    {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public UserDetailsService userDetailsService()
    {
        var user1 = User.builder().username("Harshad").password(("$argon2id$v=19$m=14,t=2,p=1$Nmg4eGR3a1dWUUZnZWIzVQ$L1rc9t+kqxcH1I3M62M2Ci3V2BzWITu9gteKhNc82r0")).roles("USER", "ADMIN").build();
        var user2 = User.builder().username("John").password(("$argon2id$v=19$m=14,t=2,p=1$Nmg4eGR3a1dWUUZnZWIzVQ$VoPfWZM7TNVMxUSj/6+QY0jI+4B0kX0QsFnDjSCYSb0")).roles("USER").build();
        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new Argon2PasswordEncoder(16, 32, 1, 1 << 14, 2);
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        var daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        var providerManager = new ProviderManager(daoAuthenticationProvider);
        return providerManager;
    }
}
