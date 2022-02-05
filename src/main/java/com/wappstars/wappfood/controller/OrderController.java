package com.wappstars.wappfood.controller;

import com.wappstars.wappfood.assembler.OrderDtoAssembler;
import com.wappstars.wappfood.dto.OrderInputDto;
import com.wappstars.wappfood.dto.OrderDto;
import com.wappstars.wappfood.model.Order;
import com.wappstars.wappfood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin
@RestController
@RequestMapping(value = "/wp-json/wf/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderDtoAssembler orderDtoAssembler;

    @Autowired
    public OrderController(OrderService orderService, OrderDtoAssembler orderDtoAssembler){
        this.orderService = orderService;
        this.orderDtoAssembler = orderDtoAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<OrderDto>> getOrders(){
        return ResponseEntity.ok(orderDtoAssembler.toCollectionModel(orderService.getOrders()));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<CollectionModel<OrderDto>> getCustomerOrders(@PathVariable("customerId") Integer customerId){
        return ResponseEntity.ok(orderDtoAssembler.toCollectionModelCustomer(orderService.getCustomerOrders(customerId), customerId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable("orderId") Integer orderId)  {
        return orderService.getOrder(orderId) //
                .map(order -> {
                    OrderDto orderDto = orderDtoAssembler.toModel(order)
                            .add(linkTo(methodOn(OrderController.class).getOrders()).withRel("orders"));

                    return ResponseEntity.ok(orderDto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> addOrder(@RequestBody @Valid OrderInputDto dto) {
        try {
            Order savedOrder = orderService.addOrder(dto);

            OrderDto orderDto = orderDtoAssembler.toModel(savedOrder)
                    .add(linkTo(methodOn(OrderController.class).getOrders()).withRel("orders"));

            return ResponseEntity //
                    .created(new URI(orderDto.getRequiredLink(IanaLinkRelations.SELF).getHref()))
                    .body(orderDto);
        } catch (URISyntaxException e) {
            return ResponseEntity.badRequest().body("Unable to create order");
        }
    }
}
