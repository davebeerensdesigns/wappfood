package com.wappstars.wappfood.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name="orders")
public class Order extends RepresentationModel<Order> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    @NotNull
    @Column(name = "customer_id")
    private Integer customerId;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(updatable = false)
    @CreationTimestamp
    private Instant dateCreated;

    @UpdateTimestamp
    private Instant dateModified;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;

    @Column
    private Boolean orderIsPayed;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private List<LineItem> lineItems;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderMeta> orderMetas;

    public enum OrderStatus {
        PENDING("Pending"),
        PROCESSING("Processing"),
        ON_HOLD("On hold"),
        COMPLETED("Completed"),
        CANCELLED("Cancelled"),
        REFUNDED("Refunded"),
        FAILED("Failed"),
        TRASH("Trash");

        String value;
        OrderStatus(String value){
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public Map<String, String> getBilling(){
        Map<String, String> billingMap = new HashMap<>();
        if(orderMetas != null) {
            for (OrderMeta metaData : orderMetas) {
                switch (metaData.getMetaKey()) {
                    case "_billing_phone" -> billingMap.put("phone", metaData.getMetaValue());
                    case "_billing_email" -> billingMap.put("email", metaData.getMetaValue());
                    case "_billing_company" -> billingMap.put("company", metaData.getMetaValue());
                    case "_billing_address" -> billingMap.put("address", metaData.getMetaValue());
                    case "_billing_city" -> billingMap.put("city", metaData.getMetaValue());
                    case "_billing_state" -> billingMap.put("state", metaData.getMetaValue());
                    case "_billing_postcode" -> billingMap.put("postcode", metaData.getMetaValue());
                    case "_billing_country" -> billingMap.put("country", metaData.getMetaValue());
                }
            }
        }
        return billingMap;
    }

    public Map<String, String> getShipping(){
        Map<String, String> shippingMap = new HashMap<>();
        if(orderMetas != null) {
            for (OrderMeta metaData : orderMetas) {
                switch (metaData.getMetaKey()) {
                    case "_shipping_company" -> shippingMap.put("company", metaData.getMetaValue());
                    case "_shipping_address" -> shippingMap.put("address", metaData.getMetaValue());
                    case "_shipping_city" -> shippingMap.put("city", metaData.getMetaValue());
                    case "_shipping_state" -> shippingMap.put("state", metaData.getMetaValue());
                    case "_shipping_postcode" -> shippingMap.put("postcode", metaData.getMetaValue());
                    case "_shipping_country" -> shippingMap.put("country", metaData.getMetaValue());
                }
            }
        }

        return shippingMap;
    }
}
