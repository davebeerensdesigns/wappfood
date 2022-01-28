package com.wappstars.wappfood.service;

import com.wappstars.wappfood.dto.UserInputDto;
import com.wappstars.wappfood.exception.*;
import com.wappstars.wappfood.model.Authority;
import com.wappstars.wappfood.model.Customer;
import com.wappstars.wappfood.model.User;
import com.wappstars.wappfood.repository.CustomerRepository;
import com.wappstars.wappfood.repository.UserRepository;
import com.wappstars.wappfood.util.HtmlToTextResolver;
import com.wappstars.wappfood.util.RandomStringGenerator;
import com.wappstars.wappfood.validators.ValidMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
            throw new EntityNotFoundException(User.class, "user id", username);
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
        if (userIdExists(user.getUsername())) throw new EntityExistsException(User.class, "username", user.getUsername());
        if (userEmailExists(user.getEmail())) throw new EntityExistsException(User.class, "email", user.getEmail());

        User newUser = new User();

        newUser.setUsername(HtmlToTextResolver.HtmlToText(user.getUsername()));

        String email = HtmlToTextResolver.HtmlToText(user.getEmail());
        if(!ValidMetaData.isValidEmail(email)){
            throw new IllegalArgumentException("Please enter a valid email address");
        } else {
            newUser.setEmail(email);
        }

        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        newUser.setApikey(randomString);

        String password = new BCryptPasswordEncoder().encode(user.getPassword());
        newUser.setPassword(password);

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

        User user = userRepository.findById(username).orElseThrow(() -> new EntityNotFoundException(User.class, "username", username));

        if(newUser.getEmail() != null && !newUser.getEmail().equals(user.getEmail())){
            if (userRepository.existsByEmail(user.getEmail())) throw new EntityExistsException(User.class, "email", user.getEmail());
            String email = HtmlToTextResolver.HtmlToText(newUser.getEmail());
            if(!ValidMetaData.isValidEmail(email)){
                throw new IllegalArgumentException("Please enter a valid email address");
            } else {
                user.setEmail(email);
            }
        }

        if(newUser.getPassword() != null) {
            String password = new BCryptPasswordEncoder().encode(newUser.getPassword());
            user.setPassword(password);
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
