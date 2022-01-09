package com.wappstars.wappfood.controller;

import com.wappstars.wappfood.dto.ProductDto;
import com.wappstars.wappfood.dto.ProductInputDto;
import com.wappstars.wappfood.exception.EntityNotFoundException;
import com.wappstars.wappfood.model.Product;
import com.wappstars.wappfood.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

//TODO: @CrossOrigin
@RestController
@RequestMapping(value = "/wp-json/wf/v1/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Object> getProducts(@RequestParam(value = "category_id", required = false) Integer categoryId){
            var dtos = new ArrayList<ProductDto>();

            List<Product> products;

            if(categoryId != null ){
                products = productService.getProductsByCategoryId(categoryId);
            } else {
                products = productService.getProducts();
            }

            for (Product product : products){
                dtos.add(ProductDto.fromProduct(product));
            }

            return ResponseEntity.ok().body(dtos);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Object> getProduct(@PathVariable("productId") Integer productId) throws EntityNotFoundException {
            var product = productService.getProduct(productId);
            return ResponseEntity.ok().body(ProductDto.fromProduct(product));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> addProduct(@RequestBody @Valid ProductInputDto dto) throws DataIntegrityViolationException {
        Integer productId = productService.addProduct(dto.toProduct());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{productId}")
                .buildAndExpand(productId).toUri();

        return ResponseEntity.created(location).body(location);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateProduct(@PathVariable("productId") Integer productId, @RequestBody @Valid Product product){
            productService.updateProduct(productId, product);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .buildAndExpand(productId).toUri();

            return ResponseEntity.created(location).body(location);

    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteProduct(@PathVariable("productId") Integer productId) throws EntityNotFoundException{
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
