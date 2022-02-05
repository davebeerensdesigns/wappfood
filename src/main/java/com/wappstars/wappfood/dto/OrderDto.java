package com.wappstars.wappfood.dto;

import com.wappstars.wappfood.model.LineItem;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@Relation(itemRelation = "order", collectionRelation = "orders")
public class OrderDto extends RepresentationModel<OrderDto> {

    private final Integer id;
    private final Integer customerId;
    private final String firstName;
    private final String lastName;
    private final Instant dateCreated;
    private final Instant dateModified;
    private final Double totalPrice;
    private final String orderStatus;
    private final Boolean orderIsPayed;
    private final Map<String, String> billing;
    private final Map<String, String> shipping;
    private final List<LineItem> lineItems;

}
