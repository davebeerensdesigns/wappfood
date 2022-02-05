package com.wappstars.wappfood.repository;

import com.wappstars.wappfood.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByUsername(String username);
    Customer getByEmail(String email);
    boolean existsByEmail(String email);
}
