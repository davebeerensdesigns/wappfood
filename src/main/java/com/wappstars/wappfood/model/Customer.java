package com.wappstars.wappfood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String username;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<CustomerMeta> customerMetas;

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

    public List<CustomerMeta> getCustomerMetas() {
        return customerMetas;
    }

    // TODO: Save billing data to customerMeta
    public Map<String, String> getBilling(){
        Map<String, String> billingMap = new HashMap<>();
        for (CustomerMeta metaData : customerMetas) {
            switch(metaData.getMetaKey()){
                case "_billing_phone":
                    billingMap.put("phone", metaData.getMetaValue());
                    break;
                case "_billing_email":
                    billingMap.put("email", metaData.getMetaValue());
                    break;
                case "_billing_company":
                    billingMap.put("company", metaData.getMetaValue());
                    break;
                case "_billing_address_1":
                    billingMap.put("address_1", metaData.getMetaValue());
                    break;
                case "_billing_address_2":
                    billingMap.put("address_2", metaData.getMetaValue());
                    break;
                case "_billing_city":
                    billingMap.put("city", metaData.getMetaValue());
                    break;
                case "_billing_state":
                    billingMap.put("state", metaData.getMetaValue());
                    break;
                case "_billing_postcode":
                    billingMap.put("postcode", metaData.getMetaValue());
                    break;
                case "_billing_country":
                    billingMap.put("country", metaData.getMetaValue());
                    break;
            }
        }

        return billingMap;
    }
    // TODO: Save shipping data to customerMeta
    public Map<String, String> getShipping(){
        Map<String, String> shippingMap = new HashMap<>();
        for (CustomerMeta metaData : customerMetas) {
            switch(metaData.getMetaKey()){
                case "_shipping_company":
                    shippingMap.put("company", metaData.getMetaValue());
                    break;
                case "_shipping_address_1":
                    shippingMap.put("address_1", metaData.getMetaValue());
                    break;
                case "_shipping_address_2":
                    shippingMap.put("address_2", metaData.getMetaValue());
                    break;
                case "_shipping_city":
                    shippingMap.put("city", metaData.getMetaValue());
                    break;
                case "_shipping_state":
                    shippingMap.put("state", metaData.getMetaValue());
                    break;
                case "_shipping_postcode":
                    shippingMap.put("postcode", metaData.getMetaValue());
                    break;
                case "_shipping_country":
                    shippingMap.put("country", metaData.getMetaValue());
                    break;
            }
        }

        return shippingMap;
    }

    public void addCustomerMeta(String metaKey, String metaValue) {
        this.customerMetas.add(new CustomerMeta(metaKey, metaValue, this));
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
