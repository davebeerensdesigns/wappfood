package com.wappstars.wappfood.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class UserInputDto {
    public String username;
    public String password;
    public Boolean enabled;
    public String apikey;
    public String email;

}