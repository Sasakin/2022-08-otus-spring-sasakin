package ru.otus.spring.book.domain;

import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

@ToString
@AllArgsConstructor
public enum Role implements GrantedAuthority {
    ADMIN("ADMIN"),
    USER("USER"),
    ANONYMOUS("ANONYMOUS");

    private final String authority;


    @Override
    public String getAuthority() {
        return authority;
    }
}
