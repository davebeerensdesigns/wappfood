package com.wappstars.wappfood.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor

@MappedSuperclass
public abstract class BaseCreatedEntity{

    @Column(updatable = false)
    @CreationTimestamp
    private Instant dateCreated;

    @UpdateTimestamp
    private Instant dateModified;
}
