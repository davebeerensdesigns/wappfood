package com.wappstars.wappfood.dto;

import com.wappstars.wappfood.model.User;

public class UserInputDto {
    public String username;
    public String password;
    public Boolean enabled;
    public String apikey;
    public String email;

    public User toUser() {

        var user = new User();

        user.setUsername(username);
        user.setPassword(password);
        user.setEnabled(enabled);
        user.setApikey(apikey);
        user.setEmail(email);

        return user;
    }

}