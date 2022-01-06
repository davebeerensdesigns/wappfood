package com.wappstars.wappfood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wappstars.wappfood.shared.BaseNameEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
public class Category extends BaseNameEntity {

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    List<Product> products;

}
