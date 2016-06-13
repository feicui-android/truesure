package com.feicuiedu.treasure.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/6/13 0013.
 */
public class User {

//    {"username":ssss, "password":dfdsfsdfsdf}

    @SerializedName("UserName")
    private String username;

    @SerializedName("Password")
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
