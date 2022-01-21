package com.wappstars.wappfood.model;

import com.wappstars.wappfood.shared.BaseCreatedEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "customer", uniqueConstraints = {@UniqueConstraint(name = "username_unique", columnNames = "username"), @UniqueConstraint(name = "customer_email_unique", columnNames = "email")})
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
    @NotNull(message = "Email is mandatory")
    @Email
    private String email;

    @Column
    private boolean isPayingCustomer = false;

    @Column
    private String username;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPayingCustomer() {
        return isPayingCustomer;
    }

    public void setPayingCustomer(boolean payingCustomer) {
        isPayingCustomer = payingCustomer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
