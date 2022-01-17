package com.wappstars.wappfood.repository;

import com.wappstars.wappfood.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsBySlug(String slug);
}
