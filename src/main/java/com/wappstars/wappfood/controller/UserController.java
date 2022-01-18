package com.wappstars.wappfood.controller;

import com.wappstars.wappfood.dto.UserDto;
import com.wappstars.wappfood.dto.UserInputDto;
import com.wappstars.wappfood.model.User;
import com.wappstars.wappfood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> getUsers() {
        var dtos = new ArrayList<UserDto>();
        List<User> users = userService.getUsers();

        for (User user : users) {
            dtos.add(UserDto.fromUser(user));
        }

        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> getUser(@PathVariable("username") String username)  {
        User user = userService.getUser(username);
        return ResponseEntity.ok().body(UserDto.fromUser(user));
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserInputDto dto) {
        String newUsername = userService.createUser(dto.toUser());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(newUsername).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<Object> updateUser(@PathVariable("username") String username, @RequestBody @Valid UserInputDto dto) {
        userService.updateUser(username, dto.toUser());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(username).toUri();

        return ResponseEntity.created(location).build();
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
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        userService.removeAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }

}
