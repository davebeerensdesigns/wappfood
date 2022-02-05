package com.wappstars.wappfood.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderInputDto {

    private Boolean orderIsPayed;
    private BillingInputDto billing;
    private ShippingInputDto shipping;
    private List<LineItemInputDto> lineItems;
}
