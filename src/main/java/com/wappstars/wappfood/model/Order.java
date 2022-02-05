package com.wappstars.wappfood.model;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private List<LineItem> lineItems;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderMeta> orderMetas;


    public Map<String, String> getBilling(){
        Map<String, String> billingMap = new HashMap<>();
        if(orderMetas != null) {
            for (OrderMeta metaData : orderMetas) {
                switch (metaData.getMetaKey()) {
                    case "_billing_phone":
                        billingMap.put("phone", metaData.getMetaValue());
                        break;
                    case "_billing_email":
                        billingMap.put("email", metaData.getMetaValue());
                        break;
                    case "_billing_company":
                        billingMap.put("company", metaData.getMetaValue());
                        break;
                    case "_billing_address":
                        billingMap.put("address", metaData.getMetaValue());
                        break;
                    case "_billing_city":
                        billingMap.put("city", metaData.getMetaValue());
                        break;
                    case "_billing_state":
                        billingMap.put("state", metaData.getMetaValue());
                        break;
                    case "_billing_postcode":
                        billingMap.put("postcode", metaData.getMetaValue());
                        break;
                    case "_billing_country":
                        billingMap.put("country", metaData.getMetaValue());
                        break;
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
                    case "_shipping_company":
                        shippingMap.put("company", metaData.getMetaValue());
                        break;
                    case "_shipping_address":
                        shippingMap.put("address", metaData.getMetaValue());
                        break;
                    case "_shipping_city":
                        shippingMap.put("city", metaData.getMetaValue());
                        break;
                    case "_shipping_state":
                        shippingMap.put("state", metaData.getMetaValue());
                        break;
                    case "_shipping_postcode":
                        shippingMap.put("postcode", metaData.getMetaValue());
                        break;
                    case "_shipping_country":
                        shippingMap.put("country", metaData.getMetaValue());
                        break;
                }
            }
        }

        return shippingMap;
    }
}
