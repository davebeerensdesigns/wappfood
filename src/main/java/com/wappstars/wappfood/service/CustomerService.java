package com.wappstars.wappfood.service;

import com.wappstars.wappfood.controller.CustomerController;
import com.wappstars.wappfood.dto.CustomerInputDto;
import com.wappstars.wappfood.exception.EntityExistsException;
import com.wappstars.wappfood.exception.EntityNotFoundException;
import com.wappstars.wappfood.model.*;
import com.wappstars.wappfood.repository.CustomerMetaRepository;
import com.wappstars.wappfood.repository.CustomerRepository;
import com.wappstars.wappfood.repository.UserRepository;
import com.wappstars.wappfood.util.HtmlToTextResolver;
import com.wappstars.wappfood.validators.ValidMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final CustomerMetaRepository customerMetaRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, UserRepository userRepository, CustomerMetaRepository customerMetaRepository){
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.customerMetaRepository = customerMetaRepository;
    }

    public List<Customer> getCustomers() {
        List<Customer> customers = customerRepository.findAll();
        if(customers.isEmpty()){
            throw new EntityNotFoundException(Customer.class);
        }
        return customers;
    }

    public Optional<Customer> getCustomer(Integer customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isEmpty()){
            throw new EntityNotFoundException(User.class, "customer id", customerId.toString());
        }
        return customer;
    }

    public boolean customerIdExists(Integer customerId) {
        return customerRepository.existsById(customerId);
    }
    public boolean customerEmailExists(String customerEmail) {
        return customerRepository.existsByEmail(customerEmail);
    }
    public boolean userIdExists(String username) {
        return userRepository.existsById(username);
    }

    public Customer addCustomer(CustomerInputDto customer) {

        Customer newCustomer = new Customer();

        if(customer.getUsername() != null) {
            String username = HtmlToTextResolver.HtmlToText(customer.getUsername());
            if (!userIdExists(username)) {
                throw new EntityNotFoundException(User.class, "username", username);
            } else {
                newCustomer.setUsername(username);
            }
        }

        String email = HtmlToTextResolver.HtmlToText(customer.getEmail());
        if(!ValidMetaData.isValidEmail(email)){
            throw new IllegalArgumentException("Please enter a valid email address");
        } else if (customerEmailExists(email)) {
                throw new EntityExistsException(Customer.class, "email", email);
        } else {
            newCustomer.setEmail(email);
        }

        if(customer.getFirstName() != null) {
            newCustomer.setFirstName(HtmlToTextResolver.HtmlToText(customer.getFirstName()));
        }
        if(customer.getLastName() != null) {
            newCustomer.setLastName(HtmlToTextResolver.HtmlToText(customer.getLastName()));
        }

        if(customer.isPayingCustomer()){
            newCustomer.setPayingCustomer(customer.isPayingCustomer());
        }

        return customerRepository.save(newCustomer);
    }

    public void deleteCustomer(Integer customerId) {
        if(!customerRepository.existsById(customerId)) throw new EntityNotFoundException(Customer.class, "id", customerId.toString());

        customerRepository.deleteById(customerId);
    }

    public Customer updateCustomer(Integer customerId, CustomerInputDto newCustomer) {

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException(Customer.class, "customer id", customerId.toString()));

        if(newCustomer.getEmail() != null){
            String email = HtmlToTextResolver.HtmlToText(newCustomer.getEmail());
            if(!ValidMetaData.isValidEmail(email)){
                throw new IllegalArgumentException("Please enter a valid email address");
            } else if (!email.equals(customer.getEmail())){
                if (customerEmailExists(email)) {
                    throw new EntityExistsException(Customer.class, "email", email);
                } else {
                    customer.setEmail(email);
                }
            }
        }

        if(newCustomer.getUsername() != null) {
            if (!customer.getUsername().equals(customer.getUsername())){
                if (!userIdExists(customer.getUsername())) {
                    throw new EntityNotFoundException(User.class, "username", customer.getUsername());
                } else {
                    customer.setUsername(newCustomer.getUsername());
                }
            }
        }

        if(newCustomer.getFirstName() != null) {
            customer.setFirstName(HtmlToTextResolver.HtmlToText(newCustomer.getFirstName()));
        }
        if(newCustomer.getLastName() != null) {
            customer.setLastName(HtmlToTextResolver.HtmlToText(newCustomer.getLastName()));
        }

        customer.setPayingCustomer((newCustomer.isPayingCustomer() == true) ? true : false);

        return customerRepository.save(customer);
    }




    public List<CustomerMeta> getCustomerMetas(Integer customerId) {
        List<CustomerMeta> customerMetas = customerMetaRepository.findByCustomerId(customerId);
        if(customerMetas.isEmpty()){
            throw new EntityNotFoundException(CustomerMeta.class, "customer_id", customerId.toString());
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
                            throw new IllegalArgumentException("Please enter a valid country");
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



    public Customer updateCustomerMeta(Integer customerId, Map<String, String> metaData, String type) {
        if (!customerMetaRepository.existsByCustomerId(customerId)) throw new EntityNotFoundException(CustomerMeta.class, "customer_id", customerId.toString());
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
                            CustomerMeta currentPhone = customerMetaRepository.findByMetaKeyAndCustomerId("_"+metaType+"_phone", customerId);
                            if(currentPhone != null){
                                String phone = HtmlToTextResolver.HtmlToText(entry.getValue());
                                if(!ValidMetaData.isValidPhone(phone)){
                                    throw new IllegalArgumentException("Please enter a valid phone number");
                                } else {
                                    currentPhone.setMetaValue(phone);
                                    customerMetaRepository.save(currentPhone);
                                }
                            }
                        }
                        break;
                    case "email":
                        if(metaType.equals("billing")){
                            CustomerMeta currentEmail = customerMetaRepository.findByMetaKeyAndCustomerId("_"+metaType+"_email", customerId);
                            if(currentEmail != null){
                                String email = HtmlToTextResolver.HtmlToText(entry.getValue());
                                if(!ValidMetaData.isValidEmail(email)){
                                    throw new IllegalArgumentException("Please enter a valid email address");
                                } else {
                                    currentEmail.setMetaValue(email);
                                    customerMetaRepository.save(currentEmail);
                                }
                            }
                        }
                        break;
                    case "company":
                        CustomerMeta currentCompany = customerMetaRepository.findByMetaKeyAndCustomerId("_"+metaType+"_company", customerId);
                        if(currentCompany != null){
                            String company = HtmlToTextResolver.HtmlToText(entry.getValue());
                            if(!ValidMetaData.isValidCompany(company)){
                                throw new IllegalArgumentException("Please enter a valid company");
                            } else {
                                currentCompany.setMetaValue(company);
                                customerMetaRepository.save(currentCompany);
                            }
                        }
                        break;
                    case "address":
                        CustomerMeta currentAddress = customerMetaRepository.findByMetaKeyAndCustomerId("_"+metaType+"_address", customerId);
                        if(currentAddress != null){
                            String address = HtmlToTextResolver.HtmlToText(entry.getValue());
                            if(!ValidMetaData.isValidAddress(address)){
                                throw new IllegalArgumentException("Please enter a valid address");
                            } else {
                                currentAddress.setMetaValue(address);
                                customerMetaRepository.save(currentAddress);
                            }
                        }
                        break;
                    case "city":
                        CustomerMeta currentCity = customerMetaRepository.findByMetaKeyAndCustomerId("_"+metaType+"_city", customerId);
                        if(currentCity != null){
                            String city = HtmlToTextResolver.HtmlToText(entry.getValue());
                            if(!ValidMetaData.isValidCity(city)){
                                throw new IllegalArgumentException("Please enter a valid city");
                            } else {
                                currentCity.setMetaValue(city);
                                customerMetaRepository.save(currentCity);
                            }
                        }
                        break;
                    case "state":
                        CustomerMeta currentState = customerMetaRepository.findByMetaKeyAndCustomerId("_"+metaType+"_state", customerId);
                        if(currentState != null){
                            String state = HtmlToTextResolver.HtmlToText(entry.getValue());
                            if(!ValidMetaData.isValidState(state)){
                                throw new IllegalArgumentException("Please enter a valid state");
                            } else {
                                currentState.setMetaValue(state);
                                customerMetaRepository.save(currentState);
                            }
                        }
                        break;
                    case "postcode":
                        CustomerMeta currentPostcode = customerMetaRepository.findByMetaKeyAndCustomerId("_"+metaType+"_postcode", customerId);
                        if(currentPostcode != null){
                            String postcode = HtmlToTextResolver.HtmlToText(entry.getValue());
                            if(!ValidMetaData.isValidPostcode(postcode)){
                                throw new IllegalArgumentException("Please enter a valid postcode");
                            } else {
                                currentPostcode.setMetaValue(postcode);
                                customerMetaRepository.save(currentPostcode);
                            }
                        }
                        break;
                    case "country":
                        CustomerMeta currentCountry = customerMetaRepository.findByMetaKeyAndCustomerId("_"+metaType+"_country", customerId);
                        if(currentCountry != null){
                            String country = HtmlToTextResolver.HtmlToText(entry.getValue());
                            if(!ValidMetaData.isValidCountry(country)){
                                throw new IllegalArgumentException("Please enter a valid country");
                            } else {
                                currentCountry.setMetaValue(country);
                                customerMetaRepository.save(currentCountry);
                            }
                        }
                        break;
                }

            }
        }
        Customer customer = customerRepository.findById(customerId).get();
        return customer;

    }

    @Transactional
    public void removeCustomerMeta(Integer customerId, ArrayList<String> metaKey) {
        if(!customerMetaRepository.existsByCustomerId(customerId))
            throw new EntityNotFoundException(CustomerMeta.class, "customer_id", customerId.toString());

        for (String array : metaKey){
            String formatted = HtmlToTextResolver.HtmlToText(array);
            if(ValidMetaData.isValidMetaKey(formatted)) {
                customerMetaRepository.deleteByMetaKeyAndCustomerId(formatted, customerId);
            }
        }
    }

    @Transactional
    public void removeAllCustomerMeta(Integer customerId) {
        if(!customerMetaRepository.existsByCustomerId(customerId))
            throw new EntityNotFoundException(CustomerMeta.class, "customer_id", customerId.toString());
        customerMetaRepository.deleteAllByCustomerId(customerId);
    }

}
