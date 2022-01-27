package com.wappstars.wappfood.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.Instant;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@Relation(itemRelation = "category", collectionRelation = "categories")
public class CategoryDto extends RepresentationModel<CategoryDto> {

    public Integer id;
    public String name;
    public String slug;
    public String description;
    public Instant dateCreated;
    public Instant dateModified;
}
