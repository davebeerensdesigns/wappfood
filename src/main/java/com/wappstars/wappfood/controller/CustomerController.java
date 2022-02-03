package com.wappstars.wappfood.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.wappstars.wappfood.assembler.CustomerDtoAssembler;
import com.wappstars.wappfood.dto.CustomerDto;
import com.wappstars.wappfood.dto.CustomerInputDto;
import com.wappstars.wappfood.dto.UserDto;
import com.wappstars.wappfood.model.Customer;
import com.wappstars.wappfood.model.CustomerMeta;
import com.wappstars.wappfood.model.User;
import com.wappstars.wappfood.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/wp-json/wf/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerDtoAssembler customerDtoAssembler;

    @Autowired
    public CustomerController(CustomerService customerService, CustomerDtoAssembler customerDtoAssembler){
        this.customerService = customerService;
        this.customerDtoAssembler = customerDtoAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<CustomerDto>> getCustomers(){
        return ResponseEntity.ok(customerDtoAssembler.toCollectionModel(customerService.getCustomers()));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("customerId") Integer customerId)  {
        return customerService.getCustomer(customerId) //
                .map(customer -> {
                    CustomerDto customerDto = customerDtoAssembler.toModel(customer)
                            .add(linkTo(methodOn(CustomerController.class).getCustomers()).withRel("customers"));

                    return ResponseEntity.ok(customerDto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> addCustomer(@RequestBody @Valid CustomerInputDto dto) {
        try {
            Customer savedCustomer = customerService.addCustomer(dto);

            CustomerDto customerDto = customerDtoAssembler.toModel(savedCustomer)
                    .add(linkTo(methodOn(CustomerController.class).getCustomers()).withRel("customers"));

            return ResponseEntity //
                    .created(new URI(customerDto.getRequiredLink(IanaLinkRelations.SELF).getHref()))
                    .body(customerDto);
        } catch (URISyntaxException e) {
            return ResponseEntity.badRequest().body("Unable to create customer");
        }
    }

    @PutMapping(value = "/{customerId}")
    public ResponseEntity<?> updateCustomer(@PathVariable("customerId") Integer customerId, @RequestBody @Valid CustomerInputDto dto) {
        Customer updatedCustomer = customerService.updateCustomer(customerId, dto);
        CustomerDto customerDto = customerDtoAssembler.toModel(updatedCustomer)
                .add(linkTo(methodOn(CustomerController.class).getCustomers()).withRel("customers"));

        return ResponseEntity.ok(customerDto);
    }

    @DeleteMapping(value = "/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("customerId") Integer customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/{customerId}/metadata")
    public ResponseEntity<Object> getCustomerMetas(@PathVariable("customerId") Integer customerId)  {
        List<CustomerMeta> customerMetas = customerService.getCustomerMetas(customerId);
        return ResponseEntity.ok().body(customerMetas);
    }

    @PostMapping("/{customerId}/billing")
    public ResponseEntity<Object> addCustomerBilling(@PathVariable("customerId") Integer customerId, @RequestBody @Valid Map<String, String> metaData) {

        Customer customer = customerService.addCustomerMeta(customerId, metaData, "billing");

        CustomerDto customerDto = customerDtoAssembler.toModel(customer)
                .add(linkTo(methodOn(CustomerController.class).getCustomers()).withRel("customers"));

        return ResponseEntity.ok(customerDto);
    }

    @PostMapping("/{customerId}/shipping")
    public ResponseEntity<Object> addCustomerShipping(@PathVariable("customerId") Integer customerId, @RequestBody @Valid Map<String, String> metaData) {

        Customer customer = customerService.addCustomerMeta(customerId, metaData, "shipping");

        CustomerDto customerDto = customerDtoAssembler.toModel(customer)
                .add(linkTo(methodOn(CustomerController.class).getCustomers()).withRel("customers"));

        return ResponseEntity.ok(customerDto);
    }

    @PutMapping("/{customerId}/billing")
    public ResponseEntity<Object> updateCustomerBilling(@PathVariable("customerId") Integer customerId, @RequestBody @Valid Map<String, String> metaData) {

        Customer customer = customerService.updateCustomerMeta(customerId, metaData, "billing");

        CustomerDto customerDto = customerDtoAssembler.toModel(customer)
                .add(linkTo(methodOn(CustomerController.class).getCustomers()).withRel("customers"));

        return ResponseEntity.ok(customerDto);
    }

    @PutMapping("/{customerId}/shipping")
    public ResponseEntity<Object> updateCustomerShipping(@PathVariable("customerId") Integer customerId, @RequestBody @Valid Map<String, String> metaData) {

        Customer customer = customerService.updateCustomerMeta(customerId, metaData, "shipping");

        CustomerDto customerDto = customerDtoAssembler.toModel(customer)
                .add(linkTo(methodOn(CustomerController.class).getCustomers()).withRel("customers"));

        return ResponseEntity.ok(customerDto);
    }

    @DeleteMapping("/{customerId}/metadata")
    public ResponseEntity<?> deleteCustomerMetas(@PathVariable("customerId") Integer customerId, @RequestBody @Valid ArrayList<String> metaData) {

        customerService.removeCustomerMeta(customerId, metaData);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{customerId}/all/metadata")
    public ResponseEntity<?> deleteCustomerMetas(@PathVariable("customerId") Integer customerId) {

        customerService.removeAllCustomerMeta(customerId);
        return ResponseEntity.noContent().build();
    }

}
