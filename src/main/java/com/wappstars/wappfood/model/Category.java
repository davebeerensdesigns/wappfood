package com.wappstars.wappfood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wappstars.wappfood.shared.BaseNameEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "category", uniqueConstraints = {@UniqueConstraint(name = "cat_slug_unique", columnNames = "slug")})
public class Category extends BaseNameEntity {

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    List<Product> products;

}
