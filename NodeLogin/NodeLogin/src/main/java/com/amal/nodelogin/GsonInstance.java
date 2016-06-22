package com.amal.nodelogin;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Adrian on 2015-04-04.
 */
public class GsonInstance {
    private static Gson gson;

    static {
        GsonBuilder gb = getGsonBuilder();
        gson = gb.create();
    }

    public static Gson instance() {
        return gson;
    }

    public static GsonBuilder getGsonBuilder() {
//        RuntimeTypeAdapterFactory<ServerResponse> serverRespAdapter =
//                RuntimeTypeAdapterFactory
//                        .of(ServerResponse.class)
//                        .registerSubtype(LoginResponse.class)
//                        .registerSubtype(ResetPasswordResponse.class);
        GsonBuilder gb = new GsonBuilder();
//        gb.registerTypeAdapterFactory(serverRespAdapter);
        gb.registerTypeAdapter(LatLng.class, new LatLngTypeAdapter());

        return gb;
    }
}
