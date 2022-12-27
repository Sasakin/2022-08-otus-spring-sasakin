package ru.otus.spring.book.security;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableGlobalMethodSecurity(
    securedEnabled = true,
    prePostEnabled = true
)
public class MethodSecurityConfiguration {
}
