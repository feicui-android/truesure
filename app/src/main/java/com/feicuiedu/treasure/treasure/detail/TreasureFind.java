package com.feicuiedu.treasure.treasure.detail;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/6/24 0024.
 */
public class TreasureFind {

    @SerializedName("Tokenid")
    private final int tokenId;

    @SerializedName("TreasureID")
    private final int treasureId;


    public TreasureFind(int tokenId, int treasureId) {
        this.tokenId = tokenId;
        this.treasureId = treasureId;
    }
}
