package com.wappstars.wappfood.dto;

import com.wappstars.wappfood.model.Category;

import java.time.Instant;

public class CategoryDto {

    public Integer id;
    public String name;
    public String slug;
    public String description;
    public Instant dateCreated;
    public Instant dateModified;

    public static CategoryDto fromCategory(Category category){
        var dto = new CategoryDto();

        dto.id = category.getId();
        dto.name = category.getName();
        dto.slug = category.getSlug();
        dto.description = category.getDescription();
        dto.dateCreated = category.getDateCreated();
        dto.dateModified = category.getDateModified();

        return dto;
    }
}
