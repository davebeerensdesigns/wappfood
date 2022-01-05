package com.wappstars.wappfood.repository;

import com.wappstars.wappfood.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
