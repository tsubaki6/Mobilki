package com.amal.nodelogin;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by adrian on 22/06/16.
 */
public class GsonInstanceTest {

    @Test
    public void testLatLngAdapter() {
        LatLng latLng = new LatLng(1, 2);
        Gson gson = GsonInstance.instance();
        String s = gson.toJson(latLng);
        assertEquals("{\"lat\":1.0,\"lng\":2.0}", s);
        LatLng latLng2 = gson.fromJson(s, LatLng.class);
        assertEquals(latLng, latLng2);
    }
}