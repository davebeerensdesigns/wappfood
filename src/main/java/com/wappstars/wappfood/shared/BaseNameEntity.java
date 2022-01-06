package com.wappstars.wappfood.shared;

import com.wappstars.wappfood.util.Slug;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class BaseNameEntity extends BaseIdEntentity{

    @Column(nullable = false, columnDefinition = "TEXT")
    @Size(max = 25)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Size(max = 50)
    private String slug;

    @Column(columnDefinition = "TEXT")
    @Size(max = 255)
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
