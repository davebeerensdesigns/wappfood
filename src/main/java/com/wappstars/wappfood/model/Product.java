package com.wappstars.wappfood.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.URL;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "product", uniqueConstraints = {@UniqueConstraint(name = "sku_unique", columnNames = "sku"), @UniqueConstraint(name = "prod_slug_unique", columnNames = "slug")})
public class Product extends RepresentationModel<Product> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    @Column(updatable = false)
    @CreationTimestamp
    private Instant dateCreated;

    @UpdateTimestamp
    private Instant dateModified;

    @Size(max = 25)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Size(max = 50)
    private String slug;

    @Size(max = 255)
    private String description;

    @Size(max = 25)
    private String sku;

    @NotNull(message = "Price is mandatory")
    private Double price;

    @Column(nullable = false)
    private Integer totalSales;

    @Column(nullable = false)
    private Integer stockQty;

    @Column(nullable = false)
    private boolean taxable;

    @Column(nullable = false)
    @Size(max = 25)
    private String taxClass;

    @URL
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "category_id_fk"), name = "category_id")
    @NotNull(message = "Category is mandatory")
    private Category category;
}
