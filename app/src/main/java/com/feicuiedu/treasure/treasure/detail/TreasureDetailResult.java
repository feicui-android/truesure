package com.feicuiedu.treasure.treasure.detail;


import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public final class TreasureDetailResult {

    @SerializedName("HTid")
    public final int id;

    @SerializedName("tdID")
    public final int treasureId;

    @SerializedName("htpoi")
    public final String location;

    /**纬度*/
    @SerializedName("htyline")
    public final double latitude;

    /**经度*/
    @SerializedName("htxline")
    public final double longitude;

    /**海拔*/
    @SerializedName("htheight")
    public final double altitude;

    @SerializedName("htsc")
    public final String description;

    @SerializedName("htbimage1")
    public final String image1;

    @SerializedName("htbimage2")
    public final String image2;

    @SerializedName("htbimage3")
    public final String image3;

    @SerializedName("htbimage4")
    public final String image4;

    @SerializedName("htbimage5")
    public final String image5;

    @SerializedName("htbimage6")
    public final String image6;

    @SerializedName("htbimage7")
    public final String image7;

    @SerializedName("htbimage8")
    public final String image8;

    @SerializedName("htbimage9")
    public final String image9;

    @SerializedName("htsize")
    public final int size;

    @SerializedName("htlevels")
    public final int level;


    public TreasureDetailResult(int id, int treasureId, String location, double latitude, double longitude, double altitude, String description, String image1, String image2, String image3, String image4, String image5, String image6, String image7, String image8, String image9, int size, int level) {
        this.id = id;
        this.treasureId = treasureId;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.description = description;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.image5 = image5;
        this.image6 = image6;
        this.image7 = image7;
        this.image8 = image8;
        this.image9 = image9;
        this.size = size;
        this.level = level;
    }

    public String[] getImages(){
        ArrayList<String> images = new ArrayList<>();
        if (!TextUtils.isEmpty(image1)) images.add(image1);
        if (!TextUtils.isEmpty(image2)) images.add(image2);
        if (!TextUtils.isEmpty(image3)) images.add(image3);
        if (!TextUtils.isEmpty(image4)) images.add(image4);
        if (!TextUtils.isEmpty(image5)) images.add(image5);
        if (!TextUtils.isEmpty(image6)) images.add(image6);
        if (!TextUtils.isEmpty(image7)) images.add(image7);
        if (!TextUtils.isEmpty(image8)) images.add(image8);
        if (!TextUtils.isEmpty(image9)) images.add(image9);
        return images.toArray(new String[images.size()]);
    }
}
