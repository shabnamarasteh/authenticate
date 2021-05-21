package com.learn.authenticate.modules.users.model.component;

public class EmailDataDTO {
    private String fullName;
    private String email;
    private String Subject;

    public EmailDataDTO(String fullName, String email, String subject) {
        this.fullName = fullName;
        this.email = email;
        Subject = subject;
    }

    public EmailDataDTO() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }
}