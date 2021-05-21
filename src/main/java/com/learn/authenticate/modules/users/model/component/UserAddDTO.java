package com.learn.authenticate.modules.users.model.component;

import com.learn.authenticate.modules.users.model.entity.User;

public class UserAddDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;

    public UserAddDTO() {
    }

    public UserAddDTO(String firstName, String lastName, String phoneNumber, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User convert2Object() {
        User user = new User();
        if(this.firstName != null) user.setFirstName(this.firstName);
        if(this.lastName != null) user.setLastName(this.lastName);
        if(this.phoneNumber != null) user.setPhoneNumber(this.phoneNumber);
        if(this.email != null) user.setEmail(this.email);
        if(this.password != null) user.setPassword(this.password);
        return user;
    }
}