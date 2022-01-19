package com.wappstars.wappfood.model;

import com.wappstars.wappfood.shared.BaseCreatedEntity;
import com.wappstars.wappfood.shared.BaseIdEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(name = "username_unique", columnNames = "username"), @UniqueConstraint(name = "user_email_unique", columnNames = "email")})
public class User extends BaseCreatedEntity {

    @Id
    @Column(nullable = false)
    @NotNull(message = "Username is mandatory")
    private String username;

    @Column(nullable = false, length = 255)
    @NotNull(message = "Password is mandatory")
    private String password;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column
    private String apikey;

    @Column
    @NotNull(message = "Email is mandatory")
    @Email
    private String email;

    @OneToMany(
            targetEntity = com.wappstars.wappfood.model.Authority.class,
            mappedBy = "username",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<com.wappstars.wappfood.model.Authority> authorities = new HashSet<>();

    public String getUsername() { return username; }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isEnabled() { return enabled;}
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public String getApikey() { return apikey; }
    public void setApikey(String apikey) { this.apikey = apikey; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email;}

    public Set<Authority> getAuthorities() { return authorities; }
    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }
    public void removeAuthority(Authority authority) {
        this.authorities.remove(authority);
    }
}