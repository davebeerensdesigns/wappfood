package com.wappstars.wappfood.repository;

import com.wappstars.wappfood.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
}
