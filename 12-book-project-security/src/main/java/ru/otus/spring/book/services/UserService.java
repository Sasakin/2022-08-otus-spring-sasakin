package ru.otus.spring.book.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.otus.spring.book.domain.User;

public interface UserService extends UserDetailsService {

    boolean saveUser(User user);
}
