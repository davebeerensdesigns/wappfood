package com.wappstars.wappfood.dto;

import com.wappstars.wappfood.model.Category;
import lombok.*;

@Builder
@Setter
@Getter
public class ProductInputDto {

    private final String name;
    private final String slug;
    private final String description;
    private final String sku;
    private final Double price;
    private final Integer totalSales;
    private final Integer stockQty;
    private final boolean taxable;
    private final String taxClass;
    private final String image;
    private final Category category;

}
