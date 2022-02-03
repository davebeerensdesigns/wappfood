package com.wappstars.wappfood.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.Instant;
import java.util.List;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@Relation(itemRelation = "line_item", collectionRelation = "line_items")
public class LineItemDto extends RepresentationModel<LineItemDto> {

    private Integer id;
    private String name;
    private Integer productId;
    private Integer quantity;
    private Double total;
    private String sku;
    private Double price;

}
