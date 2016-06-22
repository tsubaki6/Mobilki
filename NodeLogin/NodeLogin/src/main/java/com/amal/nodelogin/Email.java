package com.amal.nodelogin;

/**
 * Created by adrian on 21/06/16.
 */
public class Email {
    private String email;

    private Email(String email) {
        this.email = email;
    }

    public static final Email from(String email) {
        return new Email(email);
    }

    public String getEmail() {
        return email;
    }
}
