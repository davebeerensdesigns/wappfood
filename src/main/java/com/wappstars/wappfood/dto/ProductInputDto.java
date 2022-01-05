package com.wappstars.wappfood.dto;

import com.wappstars.wappfood.model.Category;
import com.wappstars.wappfood.model.Product;

import java.time.Instant;

public class ProductInputDto {

    public String name;
    public String slug;
    public String description;
    public Instant dateCreated;
    public Instant dateModified;
    public String sku;
    public Double price;
    public int totalSales;
    public int stockQty;
    public boolean taxable;
    public String taxClass;

    public Product toProduct(){

        var product = new Product();

        product.setName(name);
        product.setSlug(slug);
        product.setDescription(description);
        product.setDateCreated(dateCreated);
        product.setDateModified(dateModified);
        product.setSku(sku);
        product.setPrice(price);
        product.setTotalSales(totalSales);
        product.setStockQty(stockQty);
        product.setTaxable(taxable);
        product.setTaxClass(taxClass);

        return product;
    }

}
