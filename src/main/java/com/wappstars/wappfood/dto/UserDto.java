package com.wappstars.wappfood.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wappstars.wappfood.model.Authority;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.Instant;
import java.util.Set;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@Relation(itemRelation = "user", collectionRelation = "users")
public class UserDto extends RepresentationModel<UserDto> {

    private final String username;
    private final String email;
    private final Instant dateCreated;
    private final Instant dateModified;
    private final Boolean enabled;
    private final String apikey;
    @JsonSerialize
    private final Set<Authority> authorities;
}