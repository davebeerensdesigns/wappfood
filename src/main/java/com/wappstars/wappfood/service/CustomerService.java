package com.wappstars.wappfood.service;

import com.wappstars.wappfood.exception.EntityNotFoundException;
import com.wappstars.wappfood.model.*;
import com.wappstars.wappfood.repository.CustomerMetaRepository;
import com.wappstars.wappfood.repository.CustomerRepository;
import com.wappstars.wappfood.repository.UserRepository;
import com.wappstars.wappfood.util.HtmlToTextResolver;
import com.wappstars.wappfood.validators.ValidMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerMetaRepository customerMetaRepository;

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

        customerRepository.save(customer);

        return customer.getId();
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




    public List<CustomerMeta> getCustomerMetas(Integer customerId) {
        List<CustomerMeta> customerMetas = customerMetaRepository.findByCustomerId(customerId);
        if(customerMetas.isEmpty()){
            throw new EntityNotFoundException(CustomerMeta.class);
        }
        return customerMetas;
    }

    public Customer addCustomerMeta(Integer customerId, Map<String, String> metaData, String type) {
        if (!customerRepository.existsById(customerId)) throw new EntityNotFoundException(Customer.class, "customer_id", customerId.toString());

        Customer customer = customerRepository.findById(customerId).get();

        List<CustomerMeta> customerMetaData = new ArrayList<>();

        String metaType = new String();
        if(type.equals("billing")){
            metaType = "billing";
        } else if (type.equals("shipping")){
            metaType = "shipping";
        }
        if(!metaType.isEmpty()){
            for (var entry : metaData.entrySet()) {
                switch(entry.getKey()){
                    case "phone":
                        if(metaType.equals("billing")){
                            String phone = HtmlToTextResolver.HtmlToText(entry.getValue());
                            if(!ValidMetaData.isValidPhone(phone)){
                                throw new IllegalArgumentException("Please enter a valid phone number");
                            } else {
                                customerMetaData.add(new CustomerMeta("_"+metaType+"_phone", phone, customer));
                            }
                        }
                        break;
                    case "email":
                        if(metaType.equals("billing")) {
                            String email = HtmlToTextResolver.HtmlToText(entry.getValue());
                            if (!ValidMetaData.isValidEmail(email)) {
                                throw new IllegalArgumentException("Please enter a valid email address");
                            } else {
                                customerMetaData.add(new CustomerMeta("_" + metaType + "_email", email, customer));
                            }
                        }
                        break;
                    case "company":
                        String company = HtmlToTextResolver.HtmlToText(entry.getValue());
                        if(!ValidMetaData.isValidCompany(company)){
                            throw new IllegalArgumentException("Please enter a valid company");
                        } else {
                            customerMetaData.add(new CustomerMeta("_"+metaType+"_company", company, customer));
                        }
                        break;
                    case "address":
                        String address = HtmlToTextResolver.HtmlToText(entry.getValue());
                        if(!ValidMetaData.isValidAddress(address)){
                            throw new IllegalArgumentException("Please enter a valid address");
                        } else {
                            customerMetaData.add(new CustomerMeta("_"+metaType+"_address", address, customer));
                        }
                        break;
                    case "city":
                        String city = HtmlToTextResolver.HtmlToText(entry.getValue());
                        if(!ValidMetaData.isValidCity(city)){
                            throw new IllegalArgumentException("Please enter a valid city");
                        } else {
                            customerMetaData.add(new CustomerMeta("_"+metaType+"_city", city, customer));
                        }
                        break;
                    case "state":
                        String state = HtmlToTextResolver.HtmlToText(entry.getValue());
                        if(!ValidMetaData.isValidState(state)){
                            throw new IllegalArgumentException("Please enter a valid state");
                        } else {
                            customerMetaData.add(new CustomerMeta("_"+metaType+"_state", state, customer));
                        }
                        break;
                    case "postcode":
                        String postcode = HtmlToTextResolver.HtmlToText(entry.getValue());
                        if(!ValidMetaData.isValidPostcode(postcode)){
                            throw new IllegalArgumentException("Please enter a valid postcode");
                        } else {
                            customerMetaData.add(new CustomerMeta("_"+metaType+"_postcode", postcode, customer));
                        }
                        break;
                    case "country":
                        String country = HtmlToTextResolver.HtmlToText(entry.getValue());
                        if(!ValidMetaData.isValidCountry(country)){
                            throw new IllegalArgumentException("Please enter a valid country address");
                        } else {
                            customerMetaData.add(new CustomerMeta("_"+metaType+"_country", country, customer));
                        }
                        break;
                }

            }
        }


        if(!customerMetaData.isEmpty()) {
            for (CustomerMeta customerMeta : customerMetaData) {
                customerMetaRepository.save(customerMeta);
            }
        }

        return customer;

    }

}
