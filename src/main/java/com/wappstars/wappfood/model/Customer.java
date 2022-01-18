package com.wappstars.wappfood.model;

import com.wappstars.wappfood.shared.BaseCreatedEntity;

import javax.persistence.*;
import java.util.*;

@Entity
public class Customer extends BaseCreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private boolean isPayingCustomer;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    private List<CustomerMeta> customerMetas;

    public Customer() {
    }

    public Customer(String firstName, String lastName, Boolean isPayingCustomer) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isPayingCustomer = isPayingCustomer;
        this.customerMetas = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isPayingCustomer() {
        return isPayingCustomer;
    }

    public void setPayingCustomer(boolean payingCustomer) {
        isPayingCustomer = payingCustomer;
    }

    public List<CustomerMeta> getCustomerMetas() {
        return customerMetas;
    }

    public void setCustomerMetas(List<CustomerMeta> customerMetas) {
        this.customerMetas = customerMetas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer that = (Customer) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
