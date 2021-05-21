package com.learn.authenticate.modules.users.model.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "authenticate_user")
public class User implements Serializable, UserDetails {
    @Id
    @Column(name = "USER_ID", columnDefinition = "number")
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_SEQ")
    private Long userId;

    @Column(name = "FIRST_NAME", columnDefinition = "nvarchar2(200)")
    private String firstName;

    @Column(name = "LAST_NAME", columnDefinition = "nvarchar2(200)")
    private String lastName;

    @Column(name = "PHONE_NUMBER", columnDefinition = "nvarchar2(200)")
    private String phoneNumber;

    @Column(name = "EMAIL", columnDefinition = "nvarchar2(200)")
    private String email;

    @Column(name = "PASSWORD", columnDefinition = "nvarchar2(200)")
    private String password;

    public User() {
    }

    public User(Long userId, String firstName, String lastName, String phoneNumber, String email, String password) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public static UserDetails create(User user) {
        return new User(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getPassword()
        );
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

