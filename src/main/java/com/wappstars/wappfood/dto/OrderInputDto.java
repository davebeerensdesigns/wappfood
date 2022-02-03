package com.wappstars.wappfood.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderInputDto {

    private BillingDto billing;
    private ShippingDto shipping;
    private List<LineItemInputDto> lineItems;
}
