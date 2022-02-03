package com.wappstars.wappfood.dto;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInputDto {

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private boolean isPayingCustomer;

}