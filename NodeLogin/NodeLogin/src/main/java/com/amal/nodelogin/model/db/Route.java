package com.amal.nodelogin.model.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.amal.nodelogin.model.route.Leg;
import com.google.android.gms.maps.model.LatLng;

import java.util.Arrays;
import java.util.List;

/**
 * Created by adrian on 22/06/16.
 */
@Table(name = "routes")
public class Route extends Model {
    @Column(name = "name")
    private String name;
    @Column(name = "points")
    LatLng[] points;
    @Column(name = "distance")
    private Long distance;
    @Column(name = "time")
    private Long time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng[] getPoints() {
        return points;
    }

    public void setPoints(LatLng[] points) {
        this.points = points;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public static Route from(com.amal.nodelogin.model.route.Route route, List<LatLng> points, String name) {
        Route ret = new Route();
        ret.setName(name);
        long distance = 0;
        long time = 0;
        for (Leg leg : route.getLegs()) {
            distance += leg.getDistance().getValue();
            time += leg.getDuration().getValue();
        }
        ret.setDistance(distance);
        ret.setTime(time);
        ret.setPoints(points.toArray(new LatLng[points.size()]));
        return ret;
    }

    @Override
    public String toString() {
        return "Route{" +
                "name='" + name + '\'' +
                ", points=" + Arrays.toString(points) +
                ", distance=" + distance +
                ", time=" + time +
                '}';
    }
}
