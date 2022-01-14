package com.wappstars.wappfood.controller;

import com.wappstars.wappfood.dto.CategoryDto;
import com.wappstars.wappfood.dto.CategoryInputDto;
import com.wappstars.wappfood.model.Category;
import com.wappstars.wappfood.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping(value = "/wp-json/wf/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Object> getCategories(){
        var dtos = new ArrayList<CategoryDto>();
        var categories = categoryService.getCategories();

        for (Category category : categories){
            dtos.add(CategoryDto.fromCategory(category));
        }

        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Object> getCategory(@PathVariable("categoryId") Integer categoryId) {
            var category = categoryService.getCategory(categoryId);
            return ResponseEntity.ok().body(CategoryDto.fromCategory(category));
    }

    @PostMapping
    public ResponseEntity<Object> addCategory(@RequestBody @Valid CategoryInputDto dto)  {
        Integer productId = categoryService.addCategory(dto.toCategory());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{categoryId}")
                .buildAndExpand(productId).toUri();

        return ResponseEntity.created(location).body(location);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Object> updateCategory(@PathVariable("categoryId") Integer categoryId, @RequestBody @Valid Category category){
            categoryService.updateCategory(categoryId, category);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .buildAndExpand(categoryId).toUri();

            return ResponseEntity.created(location).body(location);
    }
}