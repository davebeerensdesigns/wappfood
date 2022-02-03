package com.wappstars.wappfood.repository;

import com.wappstars.wappfood.model.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineItemRepository extends JpaRepository<LineItem,Integer> {
}
