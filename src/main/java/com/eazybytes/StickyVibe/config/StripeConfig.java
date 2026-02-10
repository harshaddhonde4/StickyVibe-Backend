package com.eazybytes.StickyVibe.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:stripe.properties")
public class StripeConfig {
    @Value("${stripe.api.key}")
    private String apiKey;

    @PostConstruct
    public void init()
    {
        Stripe.apiKey = apiKey;
    }
}
