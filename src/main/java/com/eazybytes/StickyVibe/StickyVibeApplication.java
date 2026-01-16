package com.eazybytes.StickyVibe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
//@ComponentScan(basePackages = {"com.eazybytes.StickyVibe.controller"})
public class StickyVibeApplication
{

	public static void main(String[] args) {
		SpringApplication.run(StickyVibeApplication.class, args);
	}

}
