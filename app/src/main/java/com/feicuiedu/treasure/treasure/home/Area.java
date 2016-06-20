package com.feicuiedu.treasure.treasure.home;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings({"unused", "FieldCanBeLocal"})

/** 当我们获取宝藏列表数据时, 请求的数据**/
public class Area {

    //    {"currentPage":1, "PagerSize":100,YlineMin:5 ,YlineMax:6,XlineMin=5,XlineMax=6}
    @SerializedName("currentPage")
    private int currentPage = 1;

    @SerializedName("PagerSize")
    private int pagerSize = 100;

    @SerializedName("YlineMin")
    private double minLat;

    @SerializedName("YlineMax")
    private double maxLat;

    @SerializedName("XlineMin")
    private double minLng;

    @SerializedName("XlineMax")
    private double maxLng;

    public void setMinLat(double minLat) {
        this.minLat = minLat;
    }

    public void setMaxLat(double maxLat) {
        this.maxLat = maxLat;
    }

    public void setMinLng(double minLng) {
        this.minLng = minLng;
    }

    public void setMaxLng(double maxLng) {
        this.maxLng = maxLng;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Area)) return false;
        if (o == this) return true;

        Area other = (Area) o;
        return (int) maxLat == (int) other.maxLat &&
                (int) maxLng == (int) other.maxLng;
    }

    @Override
    public int hashCode() {
        return (int) maxLat;
    }
}
