package ru.otus.spring.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.book.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}