package com.wappstars.wappfood.repository;

import com.wappstars.wappfood.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByCategoryId(Integer id);
    boolean existsBySku(String sku);
}
