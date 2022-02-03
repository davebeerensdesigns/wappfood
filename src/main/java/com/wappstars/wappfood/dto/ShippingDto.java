package com.wappstars.wappfood.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ShippingDto {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String postcode;

}
