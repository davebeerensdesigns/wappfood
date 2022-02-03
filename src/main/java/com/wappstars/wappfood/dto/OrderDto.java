package com.wappstars.wappfood.dto;

import com.wappstars.wappfood.model.Customer;
import com.wappstars.wappfood.model.LineItem;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@Relation(itemRelation = "order", collectionRelation = "orders")
public class OrderDto extends RepresentationModel<OrderDto> {

    private Integer id;
    private Integer customerId;
    private Instant dateCreated;
    private Instant dateModified;
    private Double totalPrice;
    private List<LineItem> lineItems;

}
