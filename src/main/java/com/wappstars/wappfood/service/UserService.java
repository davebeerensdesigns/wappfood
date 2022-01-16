package com.wappstars.wappfood.service;

import com.wappstars.wappfood.exception.*;
import com.wappstars.wappfood.model.Authority;
import com.wappstars.wappfood.model.User;
import com.wappstars.wappfood.repository.UserRepository;
import com.wappstars.wappfood.util.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            throw new EntityNotFoundException(User.class);
        }
        return users;
    }

    public User getUser(String username) {
        return userRepository
                .findById(username)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "username", username));
    }

    public boolean userIdExists(String username) {
        return userRepository.existsById(username);
    }
    public boolean userEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public String createUser(User user) {
        if (userRepository.existsById(user.getUsername())) throw new EntityExistsException(User.class, "username", user.getUsername());
        if (userRepository.existsByEmail(user.getEmail())) throw new EntityExistsException(User.class, "email", user.getEmail());
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        user.setApikey(randomString);
        user.addAuthority(new Authority(user.getUsername(), Authority.UserRoles.DEFAULT.toString()));
        User newUser = userRepository.save(user);
        return newUser.getUsername();
    }

    public void deleteUser(String username) {
        if (!userRepository.existsById(username)) throw new EntityNotFoundException(User.class, "username", username);
        userRepository.deleteById(username);
    }

    public void updateUser(String username, User newUser) {

        if(!userRepository.existsById(username)){
            throw new EntityNotFoundException(User.class, "username", username);
        }

        User user = userRepository.findById(username).orElse(null);

        if(newUser.getEmail() != null){
            if (userRepository.existsByEmail(user.getEmail())) throw new EntityExistsException(User.class, "email", user.getEmail());
            user.setEmail(newUser.getEmail());
        }

        if(newUser.getPassword() != null) {
            user.setPassword(newUser.getPassword());
        }
        userRepository.save(user);
    }

    public Set<Authority> getUserAuthorities(String username) {
        if (!userRepository.existsById(username)) throw new EntityNotFoundException(User.class, "username", username);
        User user = userRepository.findById(username).get();
        return user.getAuthorities();
    }

    public void addAuthority(String username, String authority) {
        if (!userRepository.existsById(username)) throw new EntityNotFoundException(User.class, "username", username);

        String foundRole = null;
        String searchedValue = authority;
        ArrayList<String> values = new ArrayList<String>();

        for(Authority.UserRoles userRoles : Authority.UserRoles.values()){
            values.add(userRoles.name());
            if((userRoles.toString()).equalsIgnoreCase(searchedValue)){
                foundRole = userRoles.toString();
            }
        }

        if(foundRole == null){
            throw new OptionDoesNotExistException(Authority.class, values.toString(), "authority", authority);
        }

        User user = userRepository.findById(username).get();
        user.addAuthority(new Authority(username, foundRole));
        Instant updated = Instant.now();
        user.setDateModified(updated);
        userRepository.save(user);
    }

    public void removeAuthority(String username, String authority) {
        if (!userRepository.existsById(username)) throw new EntityNotFoundException(User.class, "username", username);
        User user = userRepository.findById(username).get();
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        if(authorityToRemove.getAuthority().equalsIgnoreCase(Authority.UserRoles.DEFAULT.toString())) {
            throw new DefaultRoleCantBeRemovedExceloption(Authority.class, "authority", Authority.UserRoles.DEFAULT.toString());
        } else {
            user.removeAuthority(authorityToRemove);
            Instant updated = Instant.now();
            user.setDateModified(updated);
            userRepository.save(user);
        }
    }

}
