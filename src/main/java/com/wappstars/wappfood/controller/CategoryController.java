package com.wappstars.wappfood.controller;

import com.wappstars.wappfood.assembler.CategoryDtoAssembler;
import com.wappstars.wappfood.dto.CategoryDto;
import com.wappstars.wappfood.dto.CategoryInputDto;
import com.wappstars.wappfood.model.Category;
import com.wappstars.wappfood.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin
@RestController
@RequestMapping(value = "/wp-json/wf/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryDtoAssembler categoryDtoAssembler;

    @Autowired
    public CategoryController(CategoryService categoryService, CategoryDtoAssembler categoryDtoAssembler){
        this.categoryService = categoryService;
        this.categoryDtoAssembler = categoryDtoAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<CategoryDto>> getCategories(){
        return ResponseEntity.ok(categoryDtoAssembler.toCollectionModel(categoryService.getCategories()));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") Integer categoryId)  {
        return categoryService.getCategory(categoryId) //
                .map(category -> {
                    CategoryDto categoryDto = categoryDtoAssembler.toModel(category)
                            .add(linkTo(methodOn(CategoryController.class).getCategories()).withRel("categories"));

                    return ResponseEntity.ok(categoryDto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody @Valid CategoryInputDto dto) {
        try {
            Category savedCategory = categoryService.addCategory(dto);

            CategoryDto categoryDto = categoryDtoAssembler.toModel(savedCategory)
                    .add(linkTo(methodOn(CategoryController.class).getCategories()).withRel("categories"));

            return ResponseEntity //
                    .created(new URI(categoryDto.getRequiredLink(IanaLinkRelations.SELF).getHref()))
                    .body(categoryDto);
        } catch (URISyntaxException e) {
            return ResponseEntity.badRequest().body("Unable to create category");
        }
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable("categoryId") Integer categoryId, @RequestBody @Valid CategoryInputDto dto){

        Category updatedCategory = categoryService.updateCategory(categoryId, dto);
        CategoryDto categoryDto = categoryDtoAssembler.toModel(updatedCategory)
                .add(linkTo(methodOn(CategoryController.class).getCategories()).withRel("categories"));
        return ResponseEntity
                .ok(categoryDto);

    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}