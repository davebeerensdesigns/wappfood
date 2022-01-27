package com.wappstars.wappfood.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.wappstars.wappfood.assembler.ProductDtoAssembler;
import com.wappstars.wappfood.dto.ProductDto;
import com.wappstars.wappfood.dto.ProductInputDto;
import com.wappstars.wappfood.model.Product;
import com.wappstars.wappfood.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;


@CrossOrigin
@RestController
@RequestMapping(value = "/wp-json/wf/v1/products")
public class ProductController {

    private final ProductService productService;
    private final ProductDtoAssembler productDtoAssembler;

    @Autowired
    public ProductController(ProductService productService, ProductDtoAssembler productDtoAssembler){
        this.productService = productService;
        this.productDtoAssembler = productDtoAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<ProductDto>> getProducts(){
        return ResponseEntity.ok(productDtoAssembler.toCollectionModel(productService.getProducts()));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("productId") Integer productId)  {
        return productService.getProduct(productId) //
            .map(product -> {
                ProductDto productDto = productDtoAssembler.toModel(product)
                        .add(linkTo(methodOn(ProductController.class).getProducts()).withRel("products"));

                return ResponseEntity.ok(productDto);
            })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody @Valid ProductInputDto dto) {
        try {
            Product savedProduct = productService.addProduct(dto.toProduct());

            ProductDto productDto = productDtoAssembler.toModel(savedProduct)
                    .add(linkTo(methodOn(ProductController.class).getProducts()).withRel("products"));

            return ResponseEntity //
                    .created(new URI(productDto.getRequiredLink(IanaLinkRelations.SELF).getHref()))
                    .body(productDto);
        } catch (URISyntaxException e) {
            return ResponseEntity.badRequest().body("Unable to create product");
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable("productId") Integer productId, @RequestBody @Valid ProductInputDto dto){

        Product updatedProduct = productService.updateProduct(productId, dto.toProduct());
        ProductDto productDto = productDtoAssembler.toModel(updatedProduct)
                .add(linkTo(methodOn(ProductController.class).getProducts()).withRel("products"));
        return ResponseEntity
                .ok(productDto);

    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") Integer productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
