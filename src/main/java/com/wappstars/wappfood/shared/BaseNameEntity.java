package com.wappstars.wappfood.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor

@MappedSuperclass
public abstract class BaseNameEntity extends BaseIdEntentity{

    @Size(max = 25)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Size(max = 50)
    private String slug;

    @Size(max = 255)
    private String description;
}
