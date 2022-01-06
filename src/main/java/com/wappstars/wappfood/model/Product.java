package com.wappstars.wappfood.model;

import com.wappstars.wappfood.shared.BaseNameEntity;
import com.wappstars.wappfood.util.Slug;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "product", uniqueConstraints = {@UniqueConstraint(name = "sku_unique", columnNames = "sku")})
public class Product extends BaseNameEntity {

    @Column(nullable = false, columnDefinition = "TEXT")
    @Size(max = 25)
    private String sku;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer totalSales;

    @Column(nullable = false)
    private Integer stockQty;

    @Column(nullable = false)
    private boolean taxable;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Size(max = 25)
    private String taxClass;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "category_id_fk"), name = "category_id")
    private Category category;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Integer totalSales) {
        this.totalSales = totalSales;
    }

    public Integer getStockQty() {
        return stockQty;
    }

    public void setStockQty(Integer stockQty) {
        this.stockQty = stockQty;
    }

    public boolean isTaxable() {
        return taxable;
    }

    public void setTaxable(boolean taxable) {
        this.taxable = taxable;
    }

    public String getTaxClass() {
        return taxClass;
    }

    public void setTaxClass(String taxClass) {
        this.taxClass = taxClass;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
