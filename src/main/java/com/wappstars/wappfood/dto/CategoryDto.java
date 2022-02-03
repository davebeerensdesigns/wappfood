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

    private final Integer id;
    private final String name;
    private final String slug;
    private final String description;
    private final String image;
    private final Instant dateCreated;
    private final Instant dateModified;
}
