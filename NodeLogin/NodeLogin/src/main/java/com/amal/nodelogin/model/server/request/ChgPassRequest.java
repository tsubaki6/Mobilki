package com.amal.nodelogin.model.server.request;

/**
 * Created by adrian on 22/06/16.
 */
public class ChgPassRequest {
    private String id;
    private String oldpass;
    private String newpass;

    public ChgPassRequest() {
    }

    public ChgPassRequest(String id, String oldpass, String newpass) {
        this.id = id;
        this.oldpass = oldpass;
        this.newpass = newpass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOldpass() {
        return oldpass;
    }

    public void setOldpass(String oldpass) {
        this.oldpass = oldpass;
    }

    public String getNewpass() {
        return newpass;
    }

    public void setNewpass(String newpass) {
        this.newpass = newpass;
    }
}
