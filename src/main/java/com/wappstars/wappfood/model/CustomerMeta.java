package com.wappstars.wappfood.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
public class CustomerMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    @Column(nullable = false)
    private String metaKey;

    @Column(nullable = false)
    private String metaValue;

    @ManyToOne
    private Customer customer;

    public CustomerMeta() {
    }

    public CustomerMeta(String metaKey, String metaValue, Customer customer) {
        this.metaKey = metaKey;
        this.metaValue = metaValue;
        this.customer = customer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMetaKey() {
        return metaKey;
    }

    public void setMetaKey(String metaKey) {
        this.metaKey = metaKey;
    }

    public String getMetaValue() {
        return metaValue;
    }

    public void setMetaValue(String metaValue) {
        this.metaValue = metaValue;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerMeta that = (CustomerMeta) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
