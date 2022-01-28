package com.wappstars.wappfood.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.Instant;
import java.util.Map;


@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@Relation(itemRelation = "customer", collectionRelation = "customers")
public class CustomerDto extends RepresentationModel<CustomerDto> {

    private final int id;
    private final Instant dateCreated;
    private final Instant dateModified;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final Boolean isPayingCustomer;
    private final String username;
    private final Map<String, String> billing;
    private final Map<String, String> shipping;
}