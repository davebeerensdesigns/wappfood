package com.wappstars.wappfood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "customer_meta", uniqueConstraints = @UniqueConstraint(name = "customer_meta_key_unique", columnNames = {"meta_key", "customer_id"}))
public class CustomerMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    @Column(name = "meta_key")
    @NotNull(message = "Metakey is mandatory")
    private String metaKey;

    @Column(name = "meta_value")
    @NotNull(message = "Metavalue is mandatory")
    private String metaValue;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    @NotNull(message = "Customer is mandatory")
    private Customer customer;

    public CustomerMeta(){}
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
}
