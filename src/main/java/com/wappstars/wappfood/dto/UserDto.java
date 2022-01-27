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

    public String username;
    public Instant dateCreated;
    public Instant dateModified;
    public String email;
    public String password;
    public Boolean enabled;
    public String apikey;
    @JsonSerialize
    public Set<Authority> authorities;
}