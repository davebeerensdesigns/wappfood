package com.wappstars.wappfood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.URL;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "category", uniqueConstraints = {@UniqueConstraint(name = "cat_slug_unique", columnNames = "slug")})
public class Category extends RepresentationModel<Category> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    @Column(updatable = false)
    @CreationTimestamp
    private Instant dateCreated;

    @UpdateTimestamp
    private Instant dateModified;

    @Size(max = 25)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Size(max = 50)
    private String slug;

    @Size(max = 255)
    private String description;

    @URL
    private String image;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Product> products;

}
