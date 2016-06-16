package com.feicuiedu.treasure.user.account;

import com.google.gson.annotations.SerializedName;

/**
 * 更新用户头像的请求体
 */
@SuppressWarnings("unused")
public class Update {

    @SerializedName("Tokenid")
    private int tokenId;

    @SerializedName("HeadPic")
    private String photoUrl;


    public void setTokenId(int tokenId) {
        this.tokenId = tokenId;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
