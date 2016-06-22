
package com.amal.nodelogin.model.route;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Route {

    @SerializedName("bounds")
    @Expose
    private Bounds bounds;
    @SerializedName("copyrights")
    @Expose
    private String copyrights;
    @SerializedName("legs")
    @Expose
    private List<Leg> legs = new ArrayList<Leg>();
    @SerializedName("overview_polyline")
    @Expose
    private Polyline overviewPolyline;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("warnings")
    @Expose
    private List<String> warnings = new ArrayList<String>();
    @SerializedName("waypoint_order")
    @Expose
    private List<Integer> waypointOrder = new ArrayList<Integer>();

    /**
     * @return The bounds
     */
    public Bounds getBounds() {
        return bounds;
    }

    /**
     * @param bounds The bounds
     */
    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    /**
     * @return The copyrights
     */
    public String getCopyrights() {
        return copyrights;
    }

    /**
     * @param copyrights The copyrights
     */
    public void setCopyrights(String copyrights) {
        this.copyrights = copyrights;
    }

    /**
     * @return The legs
     */
    public List<Leg> getLegs() {
        return legs;
    }

    /**
     * @param legs The legs
     */
    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    /**
     * @return The overviewPolyline
     */
    public Polyline getOverviewPolyline() {
        return overviewPolyline;
    }

    /**
     * @param overviewPolyline The overview_polyline
     */
    public void setOverviewPolyline(Polyline overviewPolyline) {
        this.overviewPolyline = overviewPolyline;
    }

    /**
     * @return The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary The summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return The warnings
     */
    public List<String> getWarnings() {
        return warnings;
    }

    /**
     * @param warnings The warnings
     */
    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    /**
     * @return The waypointOrder
     */
    public List<Integer> getWaypointOrder() {
        return waypointOrder;
    }

    /**
     * @param waypointOrder The waypoint_order
     */
    public void setWaypointOrder(List<Integer> waypointOrder) {
        this.waypointOrder = waypointOrder;
    }

}
