package com.wappstars.wappfood.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.wappstars.wappfood.controller.ProductController;
import com.wappstars.wappfood.dto.ProductDto;
import com.wappstars.wappfood.model.Product;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.TreeMap;

@Component
public class ProductDtoAssembler implements RepresentationModelAssembler<Product, ProductDto> {
    @Override
    public ProductDto toModel(Product entity) {

        TreeMap<String, String> category = new TreeMap<>();
        category.put("id", entity.getCategory().getId().toString());
        category.put("name", entity.getCategory().getName());
        category.put("slug", entity.getCategory().getSlug());

        ProductDto productDto = ProductDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .slug(entity.getSlug())
                .description(entity.getDescription())
                .dateCreated(entity.getDateCreated())
                .dateModified(entity.getDateModified())
                .sku(entity.getSku())
                .price(entity.getPrice())
                .totalSales(entity.getTotalSales())
                .stockQty(entity.getStockQty())
                .taxable(entity.isTaxable())
                .taxClass(entity.getTaxClass())
                .image(entity.getImage())
                .category(category)
                .build();

        productDto.add(linkTo(methodOn(ProductController.class).getProduct(productDto.getId())).withSelfRel());

        return productDto;
    }

    @Override
    public CollectionModel<ProductDto> toCollectionModel(Iterable<? extends Product> entities) {
        CollectionModel<ProductDto> productDto = RepresentationModelAssembler.super.toCollectionModel(entities);

        productDto.add(linkTo(methodOn(ProductController.class).getProducts()).withSelfRel());

        return productDto;
    }
}