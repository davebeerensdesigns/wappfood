package com.wappstars.wappfood.controller;

import com.wappstars.wappfood.dto.ProductDto;
import com.wappstars.wappfood.dto.UserDto;
import com.wappstars.wappfood.dto.UserInputDto;
import com.wappstars.wappfood.exception.EntityNotFoundException;
import com.wappstars.wappfood.model.User;
import com.wappstars.wappfood.service.ProductService;
import com.wappstars.wappfood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/wp-json/wf/v1/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        var dtos = new ArrayList<UserDto>();

        List<User> users = userService.getUsers();

        for (User user : users) {
            dtos.add(UserDto.fromUser(user));
        }

        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> getUser(@PathVariable("username") String username)  {
        var user = userService.getUser(username);
        return ResponseEntity.ok().body(UserDto.fromUser(user));
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserInputDto dto) {

        String newUsername = userService.createUser(dto.toUser());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(newUsername).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<Object> updateUser(@PathVariable("username") String username, @RequestBody UserInputDto dto) {

        userService.updateUser(username, dto.toUser());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username) {
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
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        userService.removeAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }

}
