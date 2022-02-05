package com.wappstars.wappfood.repository;

import com.wappstars.wappfood.model.CustomerMeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerMetaRepository extends JpaRepository<CustomerMeta, Integer> {
    List<CustomerMeta> findByCustomerId(Integer customerId);

    List<CustomerMeta> findByCustomerIdAndMetaKeyContains(Integer customerId, String metaKey);
    boolean existsByCustomerIdAndMetaKeyContains(Integer customerId, String metaKey);

    boolean existsByCustomerId(Integer customerId);
    boolean existsByMetaKeyAndCustomerId(String metaKey, Integer customerId);
    CustomerMeta findByMetaKeyAndCustomerId(String metaKey, Integer customerId);
    Integer deleteAllByCustomerId(Integer customerId);
    Integer deleteByMetaKeyAndCustomerId(String metaKey, Integer customerId);
}
