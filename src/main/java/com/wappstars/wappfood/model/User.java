package com.wappstars.wappfood.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(name = "username_unique", columnNames = "username"), @UniqueConstraint(name = "user_email_unique", columnNames = "email")})
public class User extends RepresentationModel<User> {

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

    @Column(updatable = false)
    @CreationTimestamp
    private Instant dateCreated;

    @UpdateTimestamp
    private Instant dateModified;

    @OneToMany(
            targetEntity = com.wappstars.wappfood.model.Authority.class,
            mappedBy = "username",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<com.wappstars.wappfood.model.Authority> authorities = new HashSet<>();

    public Set<Authority> getAuthorities() { return authorities; }
    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }
    public void removeAuthority(Authority authority) {
        this.authorities.remove(authority);
    }
}