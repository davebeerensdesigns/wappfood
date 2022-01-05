package com.wappstars.wappfood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wappstars.wappfood.shared.BaseEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "product", uniqueConstraints = {@UniqueConstraint(name = "sku_unique", columnNames = "sku")})
public class Product extends BaseEntity {

    @Column(nullable = false, columnDefinition = "TEXT")
    @Size(max = 25)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Size(max = 50)
    private String slug;

    @Column(columnDefinition = "TEXT")
    @Size(max = 255)
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Size(max = 25)
    private String sku;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private int totalSales;

    @Column(nullable = false)
    private int stockQty;

    @Column(nullable = false)
    private boolean taxable;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Size(max = 25)
    private String taxClass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public int getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    public int getStockQty() {
        return stockQty;
    }

    public void setStockQty(int stockQty) {
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
}
