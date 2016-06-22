package com.amal.nodelogin;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Martyna on 2016-05-08.
 */
class JSONAsyncTask extends AsyncTask<String, Void, JSONObject> {


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }
    JSONObject jsono;
    @Override
    protected JSONObject doInBackground(String... urls) {
        Log.d(getClass().getName(), "Invoking url: " + urls[0]);
        return new JSONObject();
    }

    protected void onPostExecute(Boolean result) {

    }

}