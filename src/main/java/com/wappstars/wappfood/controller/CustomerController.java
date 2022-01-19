package com.wappstars.wappfood.controller;

import com.wappstars.wappfood.dto.CustomerDto;
import com.wappstars.wappfood.dto.CustomerInputDto;
import com.wappstars.wappfood.model.Customer;
import com.wappstars.wappfood.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/wp-json/wf/v1/customers")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<Object> getCustomers() {
        var dtos = new ArrayList<CustomerDto>();
        List<Customer> customers = customerService.getCustomers();

        for (Customer customer : customers) {
            dtos.add(CustomerDto.fromCustomer(customer));
        }

        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Object> getCustomer(@PathVariable("customerId") Integer customerId)  {
        Customer customer = customerService.getCustomer(customerId);
        return ResponseEntity.ok().body(CustomerDto.fromCustomer(customer));
    }

    @PostMapping
    public ResponseEntity<Object> createCustomer(@RequestBody @Valid CustomerInputDto dto) {
        Integer newCustomer = customerService.createCustomer(dto.toCustomer());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{customerId}")
                .buildAndExpand(newCustomer).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{customerId}")
    public ResponseEntity<Object> updateCustomer(@PathVariable("customerId") Integer customerId, @RequestBody @Valid CustomerInputDto dto) {
        customerService.updateCustomer(customerId, dto.toCustomer());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(customerId).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/{customerId}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable("customerId") Integer customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

}
