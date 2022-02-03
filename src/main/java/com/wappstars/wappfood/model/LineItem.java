package com.wappstars.wappfood.model;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "line_items")
public class LineItem extends RepresentationModel<LineItem> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    @Column
    private String productName;

    @NotNull
    @Column
    private Integer productId;

    @NotNull
    @Column
    private Integer quantity;

    @NotNull
    @Column
    private Double price;

    @NotNull
    @Column
    private Double total;

    @Column
    private String sku;

    @Column(updatable = false)
    @CreationTimestamp
    private Instant dateCreated;

    @UpdateTimestamp
    private Instant dateModified;
}
