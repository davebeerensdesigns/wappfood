package com.wappstars.wappfood.dto;

import com.wappstars.wappfood.model.Customer;
import com.wappstars.wappfood.model.User;

public class CustomerInputDto {

    public String firstName;
    public String lastName;
    public String email;
    public String username;

    public Customer toCustomer() {

        Customer customer = new Customer();

        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setUsername(username);

        return customer;
    }

}