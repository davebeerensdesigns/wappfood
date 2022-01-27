package com.wappstars.wappfood.service;

import com.wappstars.wappfood.dto.UserDto;
import com.wappstars.wappfood.dto.UserInputDto;
import com.wappstars.wappfood.exception.*;
import com.wappstars.wappfood.model.Authority;
import com.wappstars.wappfood.model.Customer;
import com.wappstars.wappfood.model.Product;
import com.wappstars.wappfood.model.User;
import com.wappstars.wappfood.repository.CustomerRepository;
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

    @Autowired
    private CustomerRepository customerRepository;

    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            throw new EntityNotFoundException(User.class);
        }
        return users;
    }

    public Optional<User> getUser(String username) {
        Optional<User> user = userRepository.findById(username);
        if(user.isEmpty()){
            throw new EntityNotFoundException(Product.class, "user id", username);
        }
        return user;
    }

    public boolean userIdExists(String username) {
        return userRepository.existsById(username);
    }
    public boolean userEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public User addUser(UserInputDto user) {
        if (userRepository.existsById(user.getUsername())) throw new EntityExistsException(User.class, "username", user.getUsername());
        if (userRepository.existsByEmail(user.getEmail())) throw new EntityExistsException(User.class, "email", user.getEmail());

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        newUser.setApikey(randomString);
        newUser.setPassword(user.getPassword());
        newUser.addAuthority(new Authority(user.getUsername(), Authority.UserRoles.DEFAULT.toString()));
        return userRepository.save(newUser);
    }

    public void deleteUser(String username) {
        if (!userRepository.existsById(username)) throw new EntityNotFoundException(User.class, "username", username);

        Customer customer = customerRepository.findByUsername(username);
        if(customer != null) {
            customer.setUsername(null);
            customerRepository.save(customer);
        }

        userRepository.deleteById(username);
    }

    public User updateUser(String username, UserInputDto newUser) {

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
        return userRepository.save(user);
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
            throw new DefaultRoleCantBeRemovedException(Authority.class, "authority", Authority.UserRoles.DEFAULT.toString());
        } else {
            user.removeAuthority(authorityToRemove);
            Instant updated = Instant.now();
            user.setDateModified(updated);
            userRepository.save(user);
        }
    }

}
