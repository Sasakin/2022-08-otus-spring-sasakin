package ru.otus.spring.book.services;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class SecurityServices {

    @PreAuthorize("hasRole('ROLE_USER')")
    public void onlyUser() {
    }

    @Secured("ADMIN")
    public void onlyAdmin() {
    }

}
