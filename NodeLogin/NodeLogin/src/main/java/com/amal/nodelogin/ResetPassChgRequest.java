package com.amal.nodelogin;

/**
 * Created by adrian on 22/06/16.
 */
public class ResetPassChgRequest {
    private String email;
    private String code;
    private String newpass;

    public ResetPassChgRequest() {
    }

    public ResetPassChgRequest(String email, String code, String newpass) {
        this.email = email;
        this.code = code;
        this.newpass = newpass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNewpass() {
        return newpass;
    }

    public void setNewpass(String newpass) {
        this.newpass = newpass;
    }
}
