package com.amal.nodelogin.model.db;

import com.activeandroid.serializer.TypeSerializer;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by adrian on 23/06/16.
 */
public class LatLngArrayTypeSerializer extends TypeSerializer {
    @Override
    public Class<?> getDeserializedType() {
        return LatLng[].class;
    }

    @Override
    public Class<?> getSerializedType() {
        return String.class;
    }

    @Override
    public Object serialize(Object data) {
        if (data == null) return null;
        LatLng[] array = (LatLng[]) data;
        StringBuilder b = new StringBuilder();
        for (LatLng latLng : array) {
            b.append(latLng.latitude).append(",").append(latLng.longitude).append(";");
        }
        if (b.length() > 0) b.setLength(b.length() - 1);

        return b.toString();
    }

    @Override
    public Object deserialize(Object data) {
        if (data == null) return null;
        String str = (String) data;
        String[] strings = str.split(";");
        LatLng[] ret = new LatLng[strings.length];
        for (int i = 0; i < strings.length; i++) {
            String[] tmp = strings[i].split(",");
            ret[i] = new LatLng(Double.parseDouble(tmp[0]), Double.parseDouble(tmp[1]));
        }
        return ret;
    }
}
