package com.feicuiedu.treasure.user.account;


import com.google.gson.annotations.SerializedName;

public class UpdateResult {

    @SerializedName("errcode")
    private int code;

    @SerializedName("errmsg")
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
