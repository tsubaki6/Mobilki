package com.amal.nodelogin;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by adrian on 22/06/16.
 */
public interface UserApi {
    @POST("/login")
    Observable<LoginResponse> login(@Body Credentials credentials);

    @POST("/register")
    Observable<ServerResponse> register(@Body Credentials credentials);

    @POST("/api/chgpass")
    Observable<ServerResponse> changePassword(@Body ChgPassRequest request);

    @POST("/api/resetpass")
    Observable<ResetPasswordResponse> resetPassword(@Body Email email);

    @POST("/api/resetpass/chg")
    Observable<ServerResponse> resetPasswordChg(@Body ResetPassChgRequest request);
}
