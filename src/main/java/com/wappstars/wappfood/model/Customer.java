package com.wappstars.wappfood.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "customer", uniqueConstraints = {@UniqueConstraint(name = "username_unique", columnNames = "username"), @UniqueConstraint(name = "customer_email_unique", columnNames = "email")})
public class Customer extends RepresentationModel<Customer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    @Column(updatable = false)
    @CreationTimestamp
    private Instant dateCreated;

    @UpdateTimestamp
    private Instant dateModified;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    @NotNull(message = "Email is mandatory")
    @Email
    private String email;

    @Column
    private boolean isPayingCustomer = false;

    @Column
    private String username;

    @OneToMany(mappedBy = "customer")
    private Set<CustomerMeta> customerMetas;

    public Map<String, String> getBilling(){
        Map<String, String> billingMap = new HashMap<>();
        if(customerMetas != null) {
            for (CustomerMeta metaData : customerMetas) {
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
        if(customerMetas != null) {
            for (CustomerMeta metaData : customerMetas) {
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

    public Set<CustomerMeta> getCustomerMetas() { return customerMetas; }
    public void addCustomerMeta(CustomerMeta customerMeta) {
        this.customerMetas.add(customerMeta);
    }
    public void removeCustomerMeta(CustomerMeta customerMeta) {
        this.customerMetas.remove(customerMeta);
    }
}
