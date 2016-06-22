package com.amal.nodelogin;

/**
 * Created by adrian on 21/06/16.
 */
public class LoginResponse extends ServerResponse {
    private String token;
    private String grav;

    public String getToken() {
        return token;
    }

    public String getGrav() {
        return grav;
    }
}
