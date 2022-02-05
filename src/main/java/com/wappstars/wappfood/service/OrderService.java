package com.wappstars.wappfood.service;

import com.wappstars.wappfood.dto.CustomerInputDto;
import com.wappstars.wappfood.dto.LineItemInputDto;
import com.wappstars.wappfood.dto.OrderInputDto;
import com.wappstars.wappfood.exception.BadRequestException;
import com.wappstars.wappfood.exception.EntityNotFoundException;
import com.wappstars.wappfood.exception.NoStockException;
import com.wappstars.wappfood.model.*;
import com.wappstars.wappfood.repository.*;
import com.wappstars.wappfood.util.HtmlToTextResolver;
import com.wappstars.wappfood.validators.ValidMetaData;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

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

        List<OrderMeta> orderMetaData = new ArrayList<>();

        Map<String, String> shippingMap = new HashMap<>();

        Map<String, String> billingMap = new HashMap<>();

        if(dto.getBilling().getPhone() != null){
            String billingPhone = HtmlToTextResolver.HtmlToText(dto.getBilling().getPhone());
            if(!ValidMetaData.isValidPhone(billingPhone)){
                throw new IllegalArgumentException("Please enter a valid phone number");
            } else {
                OrderMeta billingMetaPhone = new OrderMeta();
                billingMetaPhone.setMetaKey("_billing_phone");
                billingMetaPhone.setMetaValue(billingPhone);
                orderMetaData.add(billingMetaPhone);
                billingMap.put("phone", billingPhone);
            }
        } else {
            throw new BadRequestException(Order.class, "phone", "mandatory");
        }

        if(dto.getBilling().getEmail() != null){
            String billingEmail = HtmlToTextResolver.HtmlToText(dto.getBilling().getEmail());
            if (!ValidMetaData.isValidEmail(billingEmail)) {
                throw new IllegalArgumentException("Please enter a valid email address");
            } else {
                OrderMeta billingMetaEmail = new OrderMeta();
                billingMetaEmail.setMetaKey("_billing_email");
                billingMetaEmail.setMetaValue(billingEmail);
                orderMetaData.add(billingMetaEmail);
                billingMap.put("email", billingEmail);
            }
        } else {
            throw new BadRequestException(Order.class, "email", "mandatory");
        }

        if(dto.getBilling().getCompany() != null){
            String billingCompany = HtmlToTextResolver.HtmlToText(dto.getBilling().getCompany());
            if(!ValidMetaData.isValidCompany(billingCompany)){
                throw new IllegalArgumentException("Please enter a valid company");
            } else {
                OrderMeta billingMetaCompany = new OrderMeta();
                billingMetaCompany.setMetaKey("_billing_company");
                billingMetaCompany.setMetaValue(billingCompany);
                orderMetaData.add(billingMetaCompany);
                billingMap.put("company", billingCompany);
            }
        }

        if(dto.getBilling().getAddress() != null){
            String billingAddress = HtmlToTextResolver.HtmlToText(dto.getBilling().getAddress());
            if(!ValidMetaData.isValidAddress(billingAddress)){
                throw new IllegalArgumentException("Please enter a valid address");
            } else {
                OrderMeta billingMetaAddress = new OrderMeta();
                billingMetaAddress.setMetaKey("_billing_address");
                billingMetaAddress.setMetaValue(billingAddress);
                orderMetaData.add(billingMetaAddress);
                billingMap.put("address", billingAddress);
            }
        }

        if(dto.getBilling().getCity() != null){
            String billingCity = HtmlToTextResolver.HtmlToText(dto.getBilling().getCity());
            if(!ValidMetaData.isValidCity(billingCity)){
                throw new IllegalArgumentException("Please enter a valid city");
            } else {
                OrderMeta billingMetaCity = new OrderMeta();
                billingMetaCity.setMetaKey("_billing_city");
                billingMetaCity.setMetaValue(billingCity);
                orderMetaData.add(billingMetaCity);
                billingMap.put("city", billingCity);
            }
        }

        if(dto.getBilling().getState() != null){
            String billingState = HtmlToTextResolver.HtmlToText(dto.getBilling().getState());
            if(!ValidMetaData.isValidState(billingState)){
                throw new IllegalArgumentException("Please enter a valid state");
            } else {
                OrderMeta billingMetaState = new OrderMeta();
                billingMetaState.setMetaKey("_billing_state");
                billingMetaState.setMetaValue(billingState);
                orderMetaData.add(billingMetaState);
                billingMap.put("state", billingState);
            }
        }

        if(dto.getBilling().getPostcode() != null){
            String billingPostcode = HtmlToTextResolver.HtmlToText(dto.getBilling().getPostcode());
            if(!ValidMetaData.isValidPostcode(billingPostcode)){
                throw new IllegalArgumentException("Please enter a valid postcode");
            } else {
                OrderMeta billingMetaPostcode = new OrderMeta();
                billingMetaPostcode.setMetaKey("_billing_postcode");
                billingMetaPostcode.setMetaValue(billingPostcode);
                orderMetaData.add(billingMetaPostcode);
                billingMap.put("postcode", billingPostcode);
            }
        }

        if(dto.getBilling().getCountry() != null){
            String billingCountry = HtmlToTextResolver.HtmlToText(dto.getBilling().getCountry());
            if(!ValidMetaData.isValidCountry(billingCountry)){
                throw new IllegalArgumentException("Please enter a valid country");
            } else {
                OrderMeta billingMetaCountry = new OrderMeta();
                billingMetaCountry.setMetaKey("_billing_country");
                billingMetaCountry.setMetaValue(billingCountry);
                orderMetaData.add(billingMetaCountry);
                billingMap.put("country", billingCountry);
            }
        }

        if(dto.getShipping().getAddress() != null){
            String shippingAddress = HtmlToTextResolver.HtmlToText(dto.getShipping().getAddress());
            if(!ValidMetaData.isValidAddress(shippingAddress)){
                throw new IllegalArgumentException("Please enter a valid address");
            } else {
                OrderMeta shippingMetaAddress = new OrderMeta();
                shippingMetaAddress.setMetaKey("_shipping_address");
                shippingMetaAddress.setMetaValue(shippingAddress);
                orderMetaData.add(shippingMetaAddress);
                shippingMap.put("address", shippingAddress);
            }
        }

        if(dto.getShipping().getCity() != null){
            String shippingCity = HtmlToTextResolver.HtmlToText(dto.getShipping().getCity());
            if(!ValidMetaData.isValidCity(shippingCity)){
                throw new IllegalArgumentException("Please enter a valid city");
            } else {
                OrderMeta shippingMetaCity = new OrderMeta();
                shippingMetaCity.setMetaKey("_shipping_city");
                shippingMetaCity.setMetaValue(shippingCity);
                orderMetaData.add(shippingMetaCity);
                shippingMap.put("city", shippingCity);
            }
        }

        if(dto.getShipping().getState() != null){
            String shippingState = HtmlToTextResolver.HtmlToText(dto.getShipping().getState());
            if(!ValidMetaData.isValidState(shippingState)){
                throw new IllegalArgumentException("Please enter a valid state");
            } else {
                OrderMeta shippingMetaState = new OrderMeta();
                shippingMetaState.setMetaKey("_shipping_state");
                shippingMetaState.setMetaValue(shippingState);
                orderMetaData.add(shippingMetaState);
                shippingMap.put("state", shippingState);
            }
        }

        if(dto.getShipping().getPostcode() != null){
            String shippingPostcode = HtmlToTextResolver.HtmlToText(dto.getShipping().getPostcode());
            if(!ValidMetaData.isValidPostcode(shippingPostcode)){
                throw new IllegalArgumentException("Please enter a valid postcode");
            } else {
                OrderMeta shippingMetaPostcode = new OrderMeta();
                shippingMetaPostcode.setMetaKey("_shipping_postcode");
                shippingMetaPostcode.setMetaValue(shippingPostcode);
                orderMetaData.add(shippingMetaPostcode);
                shippingMap.put("postcode", shippingPostcode);
            }
        }

        if(dto.getShipping().getCountry() != null){
            String shippingCountry = HtmlToTextResolver.HtmlToText(dto.getShipping().getCountry());
            if(!ValidMetaData.isValidCountry(shippingCountry)){
                throw new IllegalArgumentException("Please enter a valid country");
            } else {
                OrderMeta shippingMetaCountry = new OrderMeta();
                shippingMetaCountry.setMetaKey("_shipping_country");
                shippingMetaCountry.setMetaValue(shippingCountry);
                orderMetaData.add(shippingMetaCountry);
                shippingMap.put("country", shippingCountry);
            }
        }


        List<LineItem> lineItemList = new ArrayList<>();

        for(LineItemInputDto lineItemInputDto : dto.getLineItems()){
            Integer productId = lineItemInputDto.getProductId();
            Product product = productRepository.findById(productId).orElse(null);
            if(product != null) {
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
                if((product.getStockQty() - quantity) >= 0) {
                    product.setStockQty(product.getStockQty() - quantity);
                } else {
                    throw new NoStockException(productId, product.getStockQty());
                }
            } else {
                throw new EntityNotFoundException(Product.class, "id", productId.toString());
            }
        }

        Order order = new Order();

        boolean orderPayed = false;
        if(dto.getOrderIsPayed() == null || dto.getOrderIsPayed() == false){
            order.setOrderIsPayed(false);
            order.setOrderStatus(Order.OrderStatus.PENDING);
        } else if(dto.getOrderIsPayed() == true){
            order.setOrderIsPayed(true);
            order.setOrderStatus(Order.OrderStatus.PROCESSING);
        }

        String customerEmail = HtmlToTextResolver.HtmlToText(dto.getBilling().getEmail());
        Customer customer = customerRepository.getByEmail(customerEmail);
        User user = userRepository.getByEmail(customerEmail);

        // get or create customer by billing email
        if(customer != null){
            order.setCustomerId(customer.getId());
            order.setFirstName(customer.getFirstName());
            order.setLastName(customer.getLastName());
            order.setCustomer(customer);
            if(dto.getOrderIsPayed() == true){
                customer.setPayingCustomer(true);
            }
            customerRepository.save(customer);
        } else {
            CustomerInputDto customerInputDto = new CustomerInputDto();
            if(dto.getBilling().getFirstName() != null){
                customerInputDto.setFirstName(dto.getBilling().getFirstName());
                order.setFirstName(dto.getBilling().getFirstName());
            }
            if(dto.getBilling().getLastName() != null) {
                customerInputDto.setLastName(dto.getBilling().getLastName());
                order.setLastName(dto.getBilling().getLastName());
            }
            if(dto.getBilling().getEmail() != null) {
                customerInputDto.setEmail(dto.getBilling().getEmail());
            } else {
                throw new NullPointerException("Cant make Customer");
            }
            if(user != null){
                customerInputDto.setUsername(user.getUsername());
            }

            if(dto.getOrderIsPayed() != null && dto.getOrderIsPayed() == true){
                customerInputDto.setPayingCustomer(true);
            }
            Customer newCustomer = customerService.addCustomer(customerInputDto);

            customerService.addCustomerMeta(newCustomer.getId(), billingMap, "billing");
            customerService.addCustomerMeta(newCustomer.getId(), shippingMap, "shipping");

            order.setCustomerId(newCustomer.getId());
            order.setCustomer(newCustomer);
        }



        order.setOrderMetas(orderMetaData);

        order.setLineItems(lineItemList);

        Double orderTotal = lineItemList.stream().mapToDouble(l -> l.getTotal()).sum();
        order.setTotalPrice(orderTotal);

        // create order
        return orderRepository.save(order);
    }


    public Order setOrderPayed(Integer orderId) {
        Order order = orderRepository.getById(orderId);
        if(order != null) {
            order.setOrderIsPayed(true);
            order.setOrderStatus(Order.OrderStatus.PROCESSING);
        } else {
            throw new EntityNotFoundException(Order.class, "order id", orderId.toString());
        }
        return orderRepository.save(order);
    }


    public Order setOrderStatus(Integer orderId, Order.OrderStatus orderStatus) {
        Order order = orderRepository.getById(orderId);
        if(order != null) {
            order.setOrderStatus(orderStatus);
        } else {
            throw new EntityNotFoundException(Order.class, "order id", orderId.toString());
        }
        return orderRepository.save(order);
    }
}
