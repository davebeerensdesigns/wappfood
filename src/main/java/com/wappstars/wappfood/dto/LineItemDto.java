package com.wappstars.wappfood.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@Relation(itemRelation = "line_item", collectionRelation = "line_items")
public class LineItemDto extends RepresentationModel<LineItemDto> {

    private final Integer id;
    private final String name;
    private final Integer productId;
    private final Integer quantity;
    private final Double total;
    private final String sku;
    private final Double price;

}
