package com.wappstars.wappfood.controller;

import com.wappstars.wappfood.dto.ProductDto;
import com.wappstars.wappfood.dto.ProductInputDto;
import com.wappstars.wappfood.model.Product;
import com.wappstars.wappfood.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/wp-json/wf/v1/products")
    public List<ProductDto> getProducts(){

        var dtos = new ArrayList<ProductDto>();

        List<Product> products;

        products = productService.getProducts();

        for (Product product : products){
            dtos.add(ProductDto.fromProduct(product));
        }

        return dtos;
    }

    @GetMapping("/wp-json/wf/v1/products/{productId}")
    public ProductDto getProduct(@PathVariable("productId") Long productId){
        var product = productService.getProduct(productId);

        return ProductDto.fromProduct(product);
    }

    @PostMapping("/wp-json/wf/v1/products")
    public ProductDto addProduct(@RequestBody ProductInputDto dto){
        var product = productService.addProduct(dto.toProduct());
        return ProductDto.fromProduct(product);
    }

    @DeleteMapping("/wp-json/wf/v1/products/{productId}")
    public void deleteProduct(@PathVariable("productId") Long productId){
        productService.deleteProduct(productId);
    }

    @PutMapping("/wp-json/wf/v1/products/{productId}")
    public ProductDto updateProduct(@PathVariable("productId") Long productId, @RequestBody Product product){
        productService.updateProduct(productId, product);
        return ProductDto.fromProduct(product);
    }
}
