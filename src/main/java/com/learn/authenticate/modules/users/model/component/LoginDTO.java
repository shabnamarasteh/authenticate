package com.learn.authenticate.modules.users.model.component;

import com.learn.authenticate.modules.users.model.entity.User;

public class LoginDTO {
    private String password;
    private String email;

    public LoginDTO() {
    }

    public LoginDTO(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public User convert2Object() {
        User user = new User();
        if(this.password != null) user.setPassword(this.password);
        if(this.email != null) user.setEmail(this.email);
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}