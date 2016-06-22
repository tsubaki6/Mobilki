package com.amal.nodelogin.model.gson;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by adrian on 22/06/16.
 */
public class LatLngTypeAdapter implements JsonSerializer<LatLng>, JsonDeserializer<LatLng> {

    @Override
    public LatLng deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonNull()) return null;
        JsonObject jsonObject = json.getAsJsonObject();
        double lat = jsonObject.get("lat").getAsDouble();
        double lng = jsonObject.get("lng").getAsDouble();
        return new LatLng(lat, lng);
    }

    @Override
    public JsonElement serialize(LatLng src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null) return JsonNull.INSTANCE;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("lat", src.latitude);
        jsonObject.addProperty("lng", src.longitude);
        return jsonObject;
    }
}
