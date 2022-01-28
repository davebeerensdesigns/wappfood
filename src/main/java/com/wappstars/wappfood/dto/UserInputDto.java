package com.wappstars.wappfood.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class UserInputDto {
    private final String username;
    private final String password;
    private final Boolean enabled;
    private final String apikey;
    private final String email;

}