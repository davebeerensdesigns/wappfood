package com.wappstars.wappfood.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.wappstars.wappfood.controller.CustomerController;
import com.wappstars.wappfood.dto.CustomerDto;
import com.wappstars.wappfood.model.Customer;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class CustomerDtoAssembler implements RepresentationModelAssembler<Customer, CustomerDto> {
    @Override
    public CustomerDto toModel(Customer entity) {
        CustomerDto customerDto = CustomerDto.builder()
                .id(entity.getId())
                .dateCreated(entity.getDateCreated())
                .dateModified(entity.getDateModified())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .isPayingCustomer(entity.isPayingCustomer())
                .username(entity.getUsername())
                .billing(entity.getBilling())
                .shipping(entity.getShipping())
                .build();

        customerDto.add(linkTo(methodOn(CustomerController.class).getCustomer(customerDto.getId())).withSelfRel());

        return customerDto;
    }

    @Override
    public CollectionModel<CustomerDto> toCollectionModel(Iterable<? extends Customer> entities) {
        CollectionModel<CustomerDto> customerDto = RepresentationModelAssembler.super.toCollectionModel(entities);

        customerDto.add(linkTo(methodOn(CustomerController.class).getCustomers()).withSelfRel());

        return customerDto;
    }
}
