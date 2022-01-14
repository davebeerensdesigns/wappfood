package com.wappstars.wappfood.service;

import com.wappstars.wappfood.exception.EntityNotFoundException;
import com.wappstars.wappfood.exception.RecordNotFoundException;
import com.wappstars.wappfood.model.Authority;
import com.wappstars.wappfood.model.Product;
import com.wappstars.wappfood.model.User;
import com.wappstars.wappfood.repository.UserRepository;
import com.wappstars.wappfood.util.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private AuthorityRepository authorityRepository;

    public Collection<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(String username) {
        return userRepository
                .findById(username)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "username", username));
    }

    public boolean userExists(String username) {
        return userRepository.existsById(username);
    }

    public String createUser(User user) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        user.setApikey(randomString);
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

        user.setPassword(newUser.getPassword());
        userRepository.save(user);
    }

    public Set<Authority> getUserAuthorities(String username) {
        if (!userRepository.existsById(username)) throw new EntityNotFoundException(User.class, "username", username);
        User user = userRepository.findById(username).get();
        return user.getAuthorities();
    }

    public void addAuthority(String username, String authority) {
        if (!userRepository.existsById(username)) throw new EntityNotFoundException(User.class, "username", username);
        User user = userRepository.findById(username).get();
        user.addAuthority(new Authority(username, authority));
        userRepository.save(user);
    }

    public void removeAuthority(String username, String authority) {
        if (!userRepository.existsById(username)) throw new EntityNotFoundException(User.class, "username", username);
        User user = userRepository.findById(username).get();
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }

}
