package com.wappstars.wappfood.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.wappstars.wappfood.controller.UserController;
import com.wappstars.wappfood.dto.UserDto;
import com.wappstars.wappfood.model.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UserDtoAssembler implements RepresentationModelAssembler<User, UserDto> {
    @Override
    public UserDto toModel(User entity) {
        UserDto userDto = UserDto.builder()
                .username(entity.getUsername())
                .dateCreated(entity.getDateCreated())
                .dateModified(entity.getDateModified())
                .email(entity.getEmail())
                .enabled(entity.isEnabled())
                .apikey(entity.getApikey())
                .authorities(entity.getAuthorities())
                .build();

        userDto.add(linkTo(methodOn(UserController.class).getUser(userDto.getUsername())).withSelfRel());

        return userDto;
    }

    @Override
    public CollectionModel<UserDto> toCollectionModel(Iterable<? extends User> entities) {
        CollectionModel<UserDto> userDto = RepresentationModelAssembler.super.toCollectionModel(entities);

        userDto.add(linkTo(methodOn(UserController.class).getUsers()).withSelfRel());

        return userDto;
    }
}