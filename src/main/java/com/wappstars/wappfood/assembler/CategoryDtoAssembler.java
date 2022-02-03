package com.wappstars.wappfood.assembler;

import com.wappstars.wappfood.controller.CategoryController;
import com.wappstars.wappfood.dto.CategoryDto;
import com.wappstars.wappfood.model.Category;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CategoryDtoAssembler implements RepresentationModelAssembler<Category, CategoryDto> {
    @Override
    public CategoryDto toModel(Category entity) {

        CategoryDto categoryDto = CategoryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .slug(entity.getSlug())
                .description(entity.getDescription())
                .image(entity.getImage())
                .dateCreated(entity.getDateCreated())
                .dateModified(entity.getDateModified())
                .build();

        categoryDto.add(linkTo(methodOn(CategoryController.class).getCategory(categoryDto.getId())).withSelfRel());

        return categoryDto;
    }

    @Override
    public CollectionModel<CategoryDto> toCollectionModel(Iterable<? extends Category> entities) {
        CollectionModel<CategoryDto> categoryDto = RepresentationModelAssembler.super.toCollectionModel(entities);

        categoryDto.add(linkTo(methodOn(CategoryController.class).getCategories()).withSelfRel());

        return categoryDto;
    }
}