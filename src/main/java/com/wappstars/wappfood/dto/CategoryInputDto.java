package com.wappstars.wappfood.dto;

import com.wappstars.wappfood.model.Category;

import java.time.Instant;

public class CategoryInputDto {

    public String name;
    public String slug;
    public String description;

    public Category toCategory(){

        var category = new Category();

        category.setName(name);
        category.setSlug(slug);
        category.setDescription(description);

        return category;
    }

}
