package com.feicuiedu.treasure.treasure.home.hide;


import com.google.gson.annotations.SerializedName;

/** 埋藏宝藏响应结果实体*/
public class HideTreasureResult {

    @SerializedName("errcode")
    public int code;

    @SerializedName("errmsg")
    public String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
