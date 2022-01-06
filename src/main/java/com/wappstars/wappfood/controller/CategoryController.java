package com.wappstars.wappfood.controller;

import com.wappstars.wappfood.dto.CategoryDto;
import com.wappstars.wappfood.dto.CategoryInputDto;
import com.wappstars.wappfood.model.Category;
import com.wappstars.wappfood.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("/wp-json/wf/v1/categories")
    public ResponseEntity<Object> getCategories(){
        var dtos = new ArrayList<CategoryDto>();
        var categories = categoryService.getCategories();

        for (Category category : categories){
            dtos.add(CategoryDto.fromCategory(category));
        }

        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping("/wp-json/wf/v1/categories/{categoryId}")
    public ResponseEntity<Object> getCategory(@PathVariable("categoryId") Integer categoryId){
        return ResponseEntity.ok().body(categoryService.getCategory(categoryId));
    }

    @PostMapping("/wp-json/wf/v1/categories")
    public ResponseEntity<Object> addCategory(@RequestBody CategoryInputDto dto){
        Integer productId = categoryService.addCategory(dto.toCategory());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{categoryId}")
                .buildAndExpand(productId).toUri();

        return ResponseEntity.created(location).body(location);
    }

    @DeleteMapping("/wp-json/wf/v1/categories/{categoryId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable("categoryId") Integer categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/wp-json/wf/v1/categories/{categoryId}")
    public ResponseEntity<Object> updateCategory(@PathVariable("categoryId") Integer categoryId, @RequestBody Category category){
        categoryService.updateCategory(categoryId, category);
        return ResponseEntity.noContent().build();
    }
}