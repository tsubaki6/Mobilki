package com.amal.nodelogin;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by adrian on 22/06/16.
 */
public class App extends Application implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static App instance;

    private ServiceGenerator serviceGenerator;
    private UserApi userApi;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }

    public static App get() {
        return instance;
    }

    public ServiceGenerator getServiceGenerator() {
        if (serviceGenerator == null) serviceGenerator = new ServiceGenerator(this);
        return serviceGenerator;
    }

    public UserApi getUserApi() {
        if (userApi == null) userApi = getServiceGenerator().createService(UserApi.class);
        return userApi;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (SettingsActivity.SERVER_URL.equals(key)) {
            serviceGenerator = null;
            userApi = null;
            getServiceGenerator();
        }
    }

    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> Observable.Transformer<T, T> toastError() {
        return observable -> observable.onErrorResumeNext(throwable -> {
            Log.e(App.class.getName(), "Error: ", throwable);
            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(App.get(), "Err: " + throwable.getMessage(), Toast.LENGTH_SHORT).show());
            return Observable.empty();
        });
    }
}