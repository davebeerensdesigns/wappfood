package com.wappstars.wappfood.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wappstars.wappfood.model.Customer;
import com.wappstars.wappfood.model.CustomerMeta;
import com.wappstars.wappfood.model.User;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class CustomerDto {

    public int id;
    public Instant dateCreated;
    public Instant dateModified;
    public String firstName;
    public String lastName;
    public String email;
    public Boolean isPayingCustomer;
    public String username;
    public Map<String, String> billing;
    public Map<String, String> shipping;

    public static CustomerDto fromCustomer(Customer customer){

        var dto = new CustomerDto();

        dto.id = customer.getId();
        dto.dateCreated = customer.getDateCreated();
        dto.dateModified = customer.getDateModified();
        dto.firstName = customer.getFirstName();
        dto.lastName = customer.getLastName();
        dto.email = customer.getEmail();
        dto.isPayingCustomer = customer.isPayingCustomer();
        dto.username = customer.getUsername();
        dto.billing = customer.getBilling();
        dto.shipping = customer.getShipping();

        return dto;
    }
}