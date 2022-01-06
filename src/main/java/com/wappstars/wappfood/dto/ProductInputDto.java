package com.wappstars.wappfood.dto;

import com.wappstars.wappfood.model.Category;
import com.wappstars.wappfood.model.Product;
import com.wappstars.wappfood.util.Slug;

import java.time.Instant;

public class ProductInputDto {

    public String name;
    public String slug;
    public String description;
    public String sku;
    public Double price;
    public Integer totalSales;
    public Integer stockQty;
    public boolean taxable;
    public String taxClass;
    public Category category;

    public Product toProduct(){

        var product = new Product();

        product.setName(name);
        product.setSlug(slug);
        product.setDescription(description);
        product.setSku(sku);
        product.setPrice(price);
        product.setTotalSales(totalSales);
        product.setStockQty(stockQty);
        product.setTaxable(taxable);
        product.setTaxClass(taxClass);
        product.setCategory(category);

        return product;
    }

}
