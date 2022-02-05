package com.wappstars.wappfood.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BillingInputDto {

    private String firstName;
    private String lastName;
    private String company;
    private String address;
    private String city;
    private String state;
    private String postcode;
    private String country;
    private String email;
    private String phone;

}
