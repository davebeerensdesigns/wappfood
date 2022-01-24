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

    @OneToMany(mappedBy = "customer")
    private Set<CustomerMeta> customerMetas;

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
                case "_billing_address":
                    billingMap.put("address", metaData.getMetaValue());
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

    public Map<String, String> getShipping(){
        Map<String, String> shippingMap = new HashMap<>();
        for (CustomerMeta metaData : customerMetas) {
            switch(metaData.getMetaKey()){
                case "_shipping_company":
                    shippingMap.put("company", metaData.getMetaValue());
                    break;
                case "_shipping_address":
                    shippingMap.put("address", metaData.getMetaValue());
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

    public Set<CustomerMeta> getCustomerMetas() { return customerMetas; }
    public void addCustomerMeta(CustomerMeta customerMeta) {
        this.customerMetas.add(customerMeta);
    }
    public void removeCustomerMeta(CustomerMeta customerMeta) {
        this.customerMetas.remove(customerMeta);
    }
}
