package com.wappstars.wappfood.service;

import com.wappstars.wappfood.dto.CustomerInputDto;
import com.wappstars.wappfood.dto.LineItemInputDto;
import com.wappstars.wappfood.dto.OrderInputDto;
import com.wappstars.wappfood.exception.EntityNotFoundException;
import com.wappstars.wappfood.model.*;
import com.wappstars.wappfood.repository.CustomerRepository;
import com.wappstars.wappfood.repository.LineItemRepository;
import com.wappstars.wappfood.repository.OrderRepository;
import com.wappstars.wappfood.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductRepository productRepository;

    public List<Order> getOrders() {
        List<Order> orders = orderRepository.findAll();
        if(orders.isEmpty()){
            throw new EntityNotFoundException(Order.class);
        }
        return orders;
    }

    public List<Order> getCustomerOrders(Integer customerId) {
        List<Order> orders = orderRepository.findAllByCustomerId(customerId);
        if(orders.isEmpty()){
            throw new EntityNotFoundException(Order.class);
        }
        return orders;
    }

    public Optional<Order> getOrder(Integer orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isEmpty()){
            throw new EntityNotFoundException(Order.class, "order id", orderId.toString());
        }
        return order;
    }

    @Transactional
    public Order addOrder(OrderInputDto dto){

        Order order = new Order();

        String billingEmail = dto.getBilling().getEmail();
        Customer customer = customerRepository.findByEmail(billingEmail);

        // get or create customer by billing email
        if(customer != null){
            order.setCustomerId(customer.getId());
            order.setCustomer(customer);
        } else {
            CustomerInputDto customerInputDto = new CustomerInputDto();
            customerInputDto.setEmail(dto.getBilling().getEmail());
            Customer newCustomer = customerService.addCustomer(customerInputDto);
            order.setCustomerId(newCustomer.getId());
            order.setCustomer(newCustomer);
        }

        Order createdOrder = orderRepository.save(order);

        List<LineItem> lineItemList = new ArrayList<>();

        for(LineItemInputDto lineItemInputDto : dto.getLineItems()){
            Integer productId = lineItemInputDto.getProductId();
            Product product = productRepository.getById(productId);
            Double price = product.getPrice();
            Integer quantity = lineItemInputDto.getQuantity();

            LineItem lineItem = new LineItem();
            lineItem.setProductId(productId);
            lineItem.setProductName(product.getName());
            lineItem.setQuantity(quantity);
            lineItem.setPrice(price);
            lineItem.setTotal(quantity * price);
            lineItem.setSku(product.getSku());

            lineItemList.add(lineItem);
        }

        createdOrder.setLineItems(lineItemList);

        Double orderTotal = lineItemList.stream().mapToDouble(l -> l.getTotal()).sum();
        createdOrder.setTotalPrice(orderTotal);

        // create order
        return orderRepository.save(createdOrder);
    }

}
