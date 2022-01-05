package com.wappstars.wappfood.dto;

import com.wappstars.wappfood.model.Category;

import java.time.Instant;

public class CategoryInputDto {

    public String name;
    public String slug;
    public String description;
    public Instant dateCreated;
    public Instant dateModified;

    public Category toCategory(){

        var category = new Category();

        category.setName(name);
        category.setSlug(slug);
        category.setDescription(description);
        category.setDateCreated(dateCreated);
        category.setDateModified(dateModified);

        return category;
    }

}
