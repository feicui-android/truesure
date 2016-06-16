package com.feicuiedu.treasure.user.account;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public final class UploadResult {

    @SerializedName("error")
    private String msg;

    @SerializedName("urlcount")
    private int count;

    @SerializedName("smallImgUrl")
    private String url;

    public int getCount() {
        return count;
    }

    public String getUrl() {
        return url;
    }

    public String getMsg() {
        return msg;
    }
}
