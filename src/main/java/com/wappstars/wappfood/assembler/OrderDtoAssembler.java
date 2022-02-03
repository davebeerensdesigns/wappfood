package com.wappstars.wappfood.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.wappstars.wappfood.controller.OrderController;
import com.wappstars.wappfood.dto.OrderDto;
import com.wappstars.wappfood.model.Order;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoAssembler implements RepresentationModelAssembler<Order, OrderDto> {
    @Override
    public OrderDto toModel(Order entity) {
        OrderDto orderDto = OrderDto.builder()
                .id(entity.getId())
                .customerId(entity.getCustomerId())
                .dateCreated(entity.getDateCreated())
                .dateModified(entity.getDateModified())
                .totalPrice(entity.getTotalPrice())
                .lineItems(entity.getLineItems())
                .build();

        orderDto.add(linkTo(methodOn(OrderController.class).getOrder(orderDto.getId())).withSelfRel());

        return orderDto;
    }

    @Override
    public CollectionModel<OrderDto> toCollectionModel(Iterable<? extends Order> entities) {
        CollectionModel<OrderDto> orderDto = RepresentationModelAssembler.super.toCollectionModel(entities);

        orderDto.add(linkTo(methodOn(OrderController.class).getOrders()).withSelfRel());

        return orderDto;
    }

    public CollectionModel<OrderDto> toCollectionModelCustomer(Iterable<? extends Order> entities, Integer customerId) {
        CollectionModel<OrderDto> orderDto = RepresentationModelAssembler.super.toCollectionModel(entities);

        orderDto.add(linkTo(methodOn(OrderController.class).getCustomerOrders(customerId)).withSelfRel());

        return orderDto;
    }
}
