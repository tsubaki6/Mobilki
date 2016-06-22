package com.amal.nodelogin.model.rest;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.amal.nodelogin.gui.SettingsActivity;
import com.amal.nodelogin.model.gson.GsonInstance;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by adrian on 22/06/16.
 */
public class ServiceGenerator {
    private final Retrofit.Builder builder;

    public ServiceGenerator(Context context) {
        String baseUrl = getBaseUrl(context);
        OkHttpClient okHttpClient = createOkHttpBuilder().build();
        builder = createBuilder(context, okHttpClient, baseUrl);
    }

    private OkHttpClient.Builder createOkHttpBuilder() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(logging);
    }

    private String getBaseUrl(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String string = sharedPreferences.getString(SettingsActivity.SERVER_URL, "localhost:8080");
        return UriUtils.buildUri(string).toString();
    }

    private Retrofit.Builder createBuilder(Context context, OkHttpClient client, String url) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(client)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(GsonInstance.instance()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        return builder;
    }

    public <S> S createService(Class<S> clazz) {
        Retrofit retrofit = builder.build();
        return retrofit.create(clazz);
    }
}
