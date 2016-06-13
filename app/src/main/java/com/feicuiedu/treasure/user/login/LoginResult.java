package com.feicuiedu.treasure.user.login;

import com.google.gson.annotations.SerializedName;

/**
 * 登陆的响应结果实体
 */
public class LoginResult {

    @SerializedName("headpic")
    private String iconUrl;

    @SerializedName("tokenid")
    private int tokenId;

    @SerializedName("errcode")
    private int code;

    @SerializedName("errmsg")
    private String msg;

    public String getIconUrl() {
        return iconUrl;
    }

    public int getTokenId() {
        return tokenId;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
