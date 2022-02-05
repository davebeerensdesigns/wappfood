package com.wappstars.wappfood.service;

import com.wappstars.wappfood.dto.CustomerInputDto;
import com.wappstars.wappfood.dto.LineItemInputDto;
import com.wappstars.wappfood.dto.OrderInputDto;
import com.wappstars.wappfood.exception.EntityNotFoundException;
import com.wappstars.wappfood.model.*;
import com.wappstars.wappfood.repository.*;
import com.wappstars.wappfood.util.HtmlToTextResolver;
import com.wappstars.wappfood.validators.ValidMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
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
    private UserService userService;

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

        Map<String, String> billingMap = new HashMap<>();
        Map<String, String> shippingMap = new HashMap<>();

        if(dto.getBilling().getPhone() != null){
            OrderMeta billingMetaPhone = new OrderMeta();
            billingMetaPhone.setMetaKey("_billing_phone");
            billingMetaPhone.setMetaValue(dto.getBilling().getPhone());
            orderMetaData.add(billingMetaPhone);
            billingMap.put("phone", dto.getBilling().getPhone());
        } else {
            throw new NullPointerException("Phone is mandatory");
        }

        if(dto.getBilling().getEmail() != null){
            OrderMeta billingMetaEmail = new OrderMeta();
            billingMetaEmail.setMetaKey("_billing_email");
            billingMetaEmail.setMetaValue(dto.getBilling().getEmail());
            orderMetaData.add(billingMetaEmail);
            billingMap.put("email", dto.getBilling().getEmail());
        } else {
            throw new NullPointerException("Email is mandatory");
        }

        if(dto.getBilling().getCompany() != null){
            OrderMeta billingMetaCompany = new OrderMeta();
            billingMetaCompany.setMetaKey("_billing_company");
            billingMetaCompany.setMetaValue(dto.getBilling().getCompany());
            orderMetaData.add(billingMetaCompany);
            billingMap.put("company", dto.getBilling().getCompany());
        }

        if(dto.getBilling().getAddress() != null){
            OrderMeta billingMetaAddress = new OrderMeta();
            billingMetaAddress.setMetaKey("_billing_address");
            billingMetaAddress.setMetaValue(dto.getBilling().getAddress());
            orderMetaData.add(billingMetaAddress);
            billingMap.put("address", dto.getBilling().getAddress());
        }

        if(dto.getBilling().getCity() != null){
            OrderMeta billingMetaCity = new OrderMeta();
            billingMetaCity.setMetaKey("_billing_city");
            billingMetaCity.setMetaValue(dto.getBilling().getCity());
            orderMetaData.add(billingMetaCity);
            billingMap.put("city", dto.getBilling().getCity());
        }

        if(dto.getBilling().getState() != null){
            OrderMeta billingMetaState = new OrderMeta();
            billingMetaState.setMetaKey("_billing_state");
            billingMetaState.setMetaValue(dto.getBilling().getState());
            orderMetaData.add(billingMetaState);
            billingMap.put("state", dto.getBilling().getState());
        }

        if(dto.getBilling().getPostcode() != null){
            OrderMeta billingMetaPostcode = new OrderMeta();
            billingMetaPostcode.setMetaKey("_billing_postcode");
            billingMetaPostcode.setMetaValue(dto.getBilling().getPostcode());
            orderMetaData.add(billingMetaPostcode);
            billingMap.put("postcode", dto.getBilling().getPostcode());
        }

        if(dto.getBilling().getCountry() != null){
            OrderMeta billingMetaCountry = new OrderMeta();
            billingMetaCountry.setMetaKey("_billing_country");
            billingMetaCountry.setMetaValue(dto.getBilling().getCountry());
            orderMetaData.add(billingMetaCountry);
            billingMap.put("country", dto.getBilling().getCountry());
        }

        if(dto.getShipping().getCompany() != null){
            OrderMeta shippingMetaCompany = new OrderMeta();
            shippingMetaCompany.setMetaKey("_shipping_company");
            shippingMetaCompany.setMetaValue(dto.getShipping().getCompany());
            orderMetaData.add(shippingMetaCompany);
            shippingMap.put("company", dto.getShipping().getCompany());
        }

        if(dto.getShipping().getAddress() != null){
            OrderMeta shippingMetaAddress = new OrderMeta();
            shippingMetaAddress.setMetaKey("_shipping_address");
            shippingMetaAddress.setMetaValue(dto.getShipping().getAddress());
            orderMetaData.add(shippingMetaAddress);
            shippingMap.put("address", dto.getShipping().getAddress());
        }

        if(dto.getShipping().getCity() != null){
            OrderMeta shippingMetaCity = new OrderMeta();
            shippingMetaCity.setMetaKey("_shipping_city");
            shippingMetaCity.setMetaValue(dto.getShipping().getCity());
            orderMetaData.add(shippingMetaCity);
            shippingMap.put("city", dto.getShipping().getCity());
        }

        if(dto.getShipping().getState() != null){
            OrderMeta shippingMetaState = new OrderMeta();
            shippingMetaState.setMetaKey("_shipping_state");
            shippingMetaState.setMetaValue(dto.getShipping().getState());
            orderMetaData.add(shippingMetaState);
            shippingMap.put("state", dto.getShipping().getState());
        }

        if(dto.getShipping().getPostcode() != null){
            OrderMeta shippingMetaPostcode = new OrderMeta();
            shippingMetaPostcode.setMetaKey("_shipping_postcode");
            shippingMetaPostcode.setMetaValue(dto.getShipping().getPostcode());
            orderMetaData.add(shippingMetaPostcode);
            shippingMap.put("postcode", dto.getShipping().getPostcode());
        }

        if(dto.getShipping().getCountry() != null){
            OrderMeta shippingMetaCountry = new OrderMeta();
            shippingMetaCountry.setMetaKey("_shipping_country");
            shippingMetaCountry.setMetaValue(dto.getShipping().getCountry());
            orderMetaData.add(shippingMetaCountry);
            shippingMap.put("country", dto.getShipping().getCountry());
        }


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

        Order order = new Order();

        String billingEmail = dto.getBilling().getEmail();
        Customer customer = customerRepository.findByEmail(billingEmail);
        User user = userRepository.getByEmail(billingEmail);

        // get or create customer by billing email
        if(customer != null){
            order.setCustomerId(customer.getId());
            order.setFirstName(customer.getFirstName());
            order.setLastName(customer.getLastName());
            order.setCustomer(customer);
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
            Customer newCustomer = customerService.addCustomer(customerInputDto);

            customerService.addCustomerMeta(newCustomer.getId(), billingMap, "billing");
            customerService.addCustomerMeta(newCustomer.getId(), shippingMap, "shipping");

            order.setCustomerId(newCustomer.getId());
            order.setCustomer(newCustomer);
        }







        Order createdOrder = orderRepository.save(order);

        createdOrder.setOrderMetas(orderMetaData);

        createdOrder.setLineItems(lineItemList);

        Double orderTotal = lineItemList.stream().mapToDouble(l -> l.getTotal()).sum();
        createdOrder.setTotalPrice(orderTotal);

        // create order
        return orderRepository.save(createdOrder);
    }

}
