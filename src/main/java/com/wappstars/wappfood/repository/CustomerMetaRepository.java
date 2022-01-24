package com.wappstars.wappfood.repository;

import com.wappstars.wappfood.model.Customer;
import com.wappstars.wappfood.model.CustomerMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerMetaRepository extends JpaRepository<CustomerMeta, Integer> {
    List<CustomerMeta> findByCustomerId(Integer customerId);

    List<CustomerMeta> findByCustomerIdAndMetaKeyContains(Integer customerId, String metaKey);
}
