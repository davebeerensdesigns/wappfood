package com.wappstars.wappfood.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.wappstars.wappfood.assembler.UserDtoAssembler;
import com.wappstars.wappfood.dto.UserDto;
import com.wappstars.wappfood.dto.UserInputDto;
import com.wappstars.wappfood.model.User;
import com.wappstars.wappfood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/wp-json/wf/v1/users")
public class UserController {

    private final UserService userService;
    private final UserDtoAssembler userDtoAssembler;

    @Autowired
    public UserController(UserService userService, UserDtoAssembler userDtoAssembler){
        this.userService = userService;
        this.userDtoAssembler = userDtoAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<UserDto>> getUsers(){
        return ResponseEntity.ok(userDtoAssembler.toCollectionModel(userService.getUsers()));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable("username") String username)  {
        return userService.getUser(username) //
                .map(user -> {
                    UserDto userDto = userDtoAssembler.toModel(user)
                            .add(linkTo(methodOn(UserController.class).getUsers()).withRel("users"));

                    return ResponseEntity.ok(userDto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserInputDto dto) {
        try {
            User savedUser = userService.addUser(dto);

            UserDto userDto = userDtoAssembler.toModel(savedUser)
                    .add(linkTo(methodOn(UserController.class).getUsers()).withRel("users"));

            return ResponseEntity //
                    .created(new URI(userDto.getRequiredLink(IanaLinkRelations.SELF).getHref()))
                    .body(userDto);
        } catch (URISyntaxException e) {
            return ResponseEntity.badRequest().body("Unable to create user");
        }
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<?> updateUser(@PathVariable("username") String username, @RequestBody @Valid UserInputDto dto) {

        User updatedUser = userService.updateUser(username, dto);
        UserDto userDto = userDtoAssembler.toModel(updatedUser)
                .add(linkTo(methodOn(UserController.class).getUsers()).withRel("users"));

        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getUserAuthorities(username));
    }

    @PostMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody @Valid Map<String, Object> fields) {
        String authorityName = (String) fields.get("authority");
        userService.addAuthority(username, authorityName);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        userService.removeAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }

}
