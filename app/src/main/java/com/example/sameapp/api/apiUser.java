package com.example.sameapp.api;

import java.util.List;

public class apiUser {
    private String UserName;
    private String Password;

    public apiUser(String userName, String password) {
        UserName = userName;
        Password = password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

}
