package com.wappstars.wappfood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wappstars.wappfood.shared.BaseEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(name = "category")
public class Category extends BaseEntity {

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
