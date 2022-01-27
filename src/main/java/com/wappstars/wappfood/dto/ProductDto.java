package com.wappstars.wappfood.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.Instant;
import java.util.TreeMap;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@Relation(itemRelation = "product", collectionRelation = "products")
public class ProductDto extends RepresentationModel<ProductDto> {

    private final int id;
    private final String name;
    private final String slug;
    private final String description;
    private final Instant dateCreated;
    private final Instant dateModified;
    private final String sku;
    private final Double price;
    private final Integer totalSales;
    private final Integer stockQty;
    private final boolean taxable;
    private final String taxClass;
    private final TreeMap<String, String> category;
}
