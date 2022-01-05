package com.wappstars.wappfood.controller;

import com.wappstars.wappfood.dto.CategoryDto;
import com.wappstars.wappfood.dto.CategoryInputDto;
import com.wappstars.wappfood.model.Category;
import com.wappstars.wappfood.model.Product;
import com.wappstars.wappfood.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("/wp-json/wf/v1/categories")
    public List<CategoryDto> getCategories(){

        var dtos = new ArrayList<CategoryDto>();

        List<Category> categories;

        categories = categoryService.getCategories();

        for (Category category : categories){
            dtos.add(CategoryDto.fromCategory(category));
        }

        return dtos;
    }

    @GetMapping("/wp-json/wf/v1/categories/{categoryId}")
    public CategoryDto getCategory(@PathVariable("categoryId") Long categoryId){
        var category = categoryService.getCategory(categoryId);

        return CategoryDto.fromCategory(category);
    }

    @PostMapping("/wp-json/wf/v1/categories")
    public CategoryDto addCategory(@RequestBody CategoryInputDto dto){
        var category = categoryService.addCategory(dto.toCategory());
        return CategoryDto.fromCategory(category);
    }

    @DeleteMapping("/wp-json/wf/v1/categories/{categoryId}")
    public void deleteCategory(@PathVariable("categoryId") Long categoryId){
        categoryService.deleteCategory(categoryId);
    }

    @PutMapping("/wp-json/wf/v1/categories/{categoryId}")
    public CategoryDto updateCategory(@PathVariable("categoryId") Long categoryId, @RequestBody Category category){
        categoryService.updateCategory(categoryId, category);
        return CategoryDto.fromCategory(category);
    }
}