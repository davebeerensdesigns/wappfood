package com.wappstars.wappfood.service;

import com.wappstars.wappfood.exception.EntityExistsException;
import com.wappstars.wappfood.exception.EntityNotFoundException;
import com.wappstars.wappfood.model.Authority;
import com.wappstars.wappfood.model.Customer;
import com.wappstars.wappfood.model.Product;
import com.wappstars.wappfood.model.User;
import com.wappstars.wappfood.repository.CustomerRepository;
import com.wappstars.wappfood.repository.UserRepository;
import com.wappstars.wappfood.util.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Customer> getCustomers() {
        List<Customer> customers = customerRepository.findAll();
        if(customers.isEmpty()){
            throw new EntityNotFoundException(Customer.class);
        }
        return customers;
    }

    public Customer getCustomer(Integer customerId) {
        return customerRepository
                .findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException(Customer.class, "id", customerId.toString()));
    }

    public boolean customerIdExists(Integer customerId) {
        return customerRepository.existsById(customerId);
    }

    public Integer createCustomer(Customer customer) {
        if(customer.getUsername() != null){
            if(!userRepository.existsById(customer.getUsername())){
                throw new EntityNotFoundException(User.class, "username", customer.getUsername());
            }
        }
        Customer newCustomer = customerRepository.save(customer);
        return newCustomer.getId();
    }

    public void deleteCustomer(Integer customerId) {
        if(!customerRepository.existsById(customerId)){
            throw new EntityNotFoundException(Customer.class, "id", customerId.toString());
        }
        customerRepository.deleteById(customerId);
    }

    public void updateCustomer(Integer customerId, Customer newCustomer) {
        if(!customerRepository.existsById(customerId)){
            throw new EntityNotFoundException(Customer.class, "customer id", customerId.toString());
        }

        Customer customer = customerRepository.findById(customerId).orElse(null);

        if(newCustomer.getFirstName() != null){
            customer.setFirstName(newCustomer.getFirstName());
        }

        if(newCustomer.getLastName() != null){
            customer.setLastName(newCustomer.getLastName());
        }

        if(newCustomer.getEmail() != null){
            customer.setEmail(newCustomer.getEmail());
        }

        customer.setPayingCustomer((newCustomer.isPayingCustomer()) ? true : false);

        if(newCustomer.getUsername() != null){
            if(!userRepository.existsById(newCustomer.getUsername())){
                throw new EntityNotFoundException(User.class, "username", newCustomer.getUsername());
            }
            customer.setUsername(newCustomer.getUsername());
        }

        customerRepository.save(customer);
    }

}
