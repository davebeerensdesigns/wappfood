package com.wappstars.wappfood.model;

import com.wappstars.wappfood.shared.BaseNameEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "product", uniqueConstraints = {@UniqueConstraint(name = "sku_unique", columnNames = "sku"), @UniqueConstraint(name = "prod_slug_unique", columnNames = "slug")})
public class Product extends BaseNameEntity {

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "category_id_fk"), name = "category_id")
    @NotNull(message = "Category is mandatory")
    private Category category;

}
