package com.wappstars.wappfood.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ShippingDto {

    private String company;
    private String address;
    private String city;
    private String state;
    private String postcode;
    private String country;

}
