
package com.amal.nodelogin.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bounds {

    @SerializedName("northeast")
    @Expose
    private LatLng northeast;
    @SerializedName("southwest")
    @Expose
    private LatLng southwest;

    public LatLng getNortheast() {
        return northeast;
    }

    public void setNortheast(LatLng northeast) {
        this.northeast = northeast;
    }

    public LatLng getSouthwest() {
        return southwest;
    }

    public void setSouthwest(LatLng southwest) {
        this.southwest = southwest;
    }
}
