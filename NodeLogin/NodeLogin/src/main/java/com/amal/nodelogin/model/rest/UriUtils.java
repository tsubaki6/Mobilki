package com.amal.nodelogin.model.rest;

import android.net.Uri;

/**
 * Created by adrian on 2/28/16.
 */
public class UriUtils {

    public static Uri.Builder buildUri(String address, int port) {
        return buildUri(address + ":" + port);
    }

    public static Uri.Builder buildUri(String addresWithPort) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .encodedAuthority(addresWithPort);
        return builder;
    }
}
