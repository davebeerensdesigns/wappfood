package com.wappstars.wappfood.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class CategoryInputDto {

    private final String name;
    private final String slug;
    private final String description;

}
