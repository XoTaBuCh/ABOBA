package com.javamaster.spring_crud.repository;

import com.javamaster.spring_crud.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
