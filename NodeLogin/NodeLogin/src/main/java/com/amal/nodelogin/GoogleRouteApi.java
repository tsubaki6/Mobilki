package com.amal.nodelogin;

import com.amal.nodelogin.model.GoogleRoutesResponse;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by adrian on 22/06/16.
 */
public interface GoogleRouteApi {

    @GET
    Observable<GoogleRoutesResponse> route(@Url String url);
}
