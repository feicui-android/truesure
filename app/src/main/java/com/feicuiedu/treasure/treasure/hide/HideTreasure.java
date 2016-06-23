package com.feicuiedu.treasure.treasure.hide;

import com.google.gson.annotations.SerializedName;

/** 埋藏宝藏请求用的实体*/
public class HideTreasure {

    @SerializedName("Tokenid")
    private int tokenId;

    @SerializedName("TreasureName")
    private String title;

    @SerializedName("POI")
    private String location;

    @SerializedName("ShortContent")
    private String description;

    @SerializedName("Yline")
    private double latitude;

    @SerializedName("Xline")
    private double longitude;

    @SerializedName("Height")
    private double altitude;

    public void setTokenId(int tokenId) {
        this.tokenId = tokenId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    @SerializedName("Size")
    private final int size = 1;

    @SerializedName("Levels")
    private final int level = 1;

    @SerializedName("Bimage1")
    private final String imageUrl1 = "";

    @SerializedName("Bimage2")
    private final String imageUrl2 = "";

    @SerializedName("Bimage3")
    private final String imageUrl3 = "";

    @SerializedName("Bimage4")
    private final String imageUrl4 = "";

    @SerializedName("Bimage5")
    private final String imageUrl5 = "";

    @SerializedName("Bimage6")
    private final String imageUrl6 = "";

    @SerializedName("Bimage7")
    private final String imageUrl7 = "";

    @SerializedName("Bimage8")
    private final String imageUrl8 = "";

    @SerializedName("Bimage9")
    private final String imageUrl9  = "";
}
