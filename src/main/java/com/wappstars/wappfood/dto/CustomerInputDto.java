package com.wappstars.wappfood.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class CustomerInputDto {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String username;
    private final boolean isPayingCustomer;

}