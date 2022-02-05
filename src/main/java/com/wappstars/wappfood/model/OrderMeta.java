package com.wappstars.wappfood.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "order_meta")
public class OrderMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    @Column(name = "meta_key")
    @NotNull(message = "Metakey is mandatory")
    private String metaKey;

    @Column(name = "meta_value")
    @NotNull(message = "Metavalue is mandatory")
    private String metaValue;
}
