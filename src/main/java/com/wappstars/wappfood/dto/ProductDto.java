package com.wappstars.wappfood.dto;

import com.wappstars.wappfood.model.Category;
import com.wappstars.wappfood.model.Product;

import java.time.Instant;

public class ProductDto {

    public int id;
    public String name;
    public String slug;
    public String description;
    public Instant dateCreated;
    public Instant dateModified;
    public String sku;
    public Double price;
    public Integer totalSales;
    public Integer stockQty;
    public boolean taxable;
    public String taxClass;
    public Category category;

    public static ProductDto fromProduct(Product product){
        var dto = new ProductDto();

        dto.id = product.getId();
        dto.name = product.getName();
        dto.slug = product.getSlug();
        dto.description = product.getDescription();
        dto.dateCreated = product.getDateCreated();
        dto.dateModified = product.getDateModified();
        dto.sku = product.getSku();
        dto.price = product.getPrice();
        dto.totalSales = product.getTotalSales();
        dto.stockQty = product.getStockQty();
        dto.taxable = product.isTaxable();
        dto.taxClass = product.getTaxClass();
        dto.category = product.getCategory();

        return dto;
    }
}
