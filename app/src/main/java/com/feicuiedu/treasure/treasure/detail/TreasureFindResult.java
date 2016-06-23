package com.feicuiedu.treasure.treasure.detail;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/6/24 0024.
 */
public class TreasureFindResult {

    @SerializedName("errcode")
    private final int code;

    @SerializedName("errmsg")
    private final String msg;

    public TreasureFindResult(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
