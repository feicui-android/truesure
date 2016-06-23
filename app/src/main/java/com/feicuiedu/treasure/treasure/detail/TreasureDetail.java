package com.feicuiedu.treasure.treasure.detail;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("all")
public class TreasureDetail {

    @SerializedName("TreasureID")
    private final int treasureId;

    @SerializedName("PagerSize")
    private final int pageSize;

    @SerializedName("currentPage")
    private final int currentPage;

    public TreasureDetail(int treasureId, int pageSize, int currentPage) {
        this.treasureId = treasureId;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }
}
