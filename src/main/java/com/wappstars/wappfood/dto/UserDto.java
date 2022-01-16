package com.wappstars.wappfood.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wappstars.wappfood.model.Authority;
import com.wappstars.wappfood.model.User;

import java.time.Instant;
import java.util.Set;

public class UserDto {

    public String username;
    public String password;
    public Boolean enabled;
    public String apikey;
    public String email;
    public Instant dateCreated;
    public Instant dateModified;
    @JsonSerialize
    public Set<Authority> authorities;

    public static UserDto fromUser(User user){

        var dto = new UserDto();

        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.enabled = user.isEnabled();
        dto.apikey = user.getApikey();
        dto.email = user.getEmail();
        dto.dateCreated = user.getDateCreated();
        dto.dateModified = user.getDateModified();
        dto.authorities = user.getAuthorities();

        return dto;
    }
}