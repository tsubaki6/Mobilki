package com.amal.nodelogin;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class ProfileActivity extends Activity {

    SharedPreferences pref;
    String token, grav, oldpasstxt, newpasstxt;
    WebView web;
    Button chgpass, chgpassfr, cancel, logout;
    Dialog dlg;
    EditText oldpass, newpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        web = (WebView) findViewById(R.id.webView);
        chgpass = (Button) findViewById(R.id.chgbtn);
        logout = (Button) findViewById(R.id.logout);

        pref = getSharedPreferences("AppPref", MODE_PRIVATE);
        token = pref.getString("token", "");
        grav = pref.getString("grav", "");

        web.getSettings().setUseWideViewPort(true);
        web.getSettings().setLoadWithOverviewMode(true);
        web.loadUrl(grav);
    }

    @OnClick(R.id.logout)
    public void onLogoutClick() {
        SharedPreferences.Editor edit = pref.edit();
        //Storing Data using SharedPreferences
        edit.putString("token", "");
        edit.commit();
        Intent loginactivity = new Intent(ProfileActivity.this, LoginActivity.class);

        startActivity(loginactivity);
        finish();
    }


    @OnClick(R.id.chgbtn)
    public void OnChgBtnClick() {
        dlg = new Dialog(ProfileActivity.this);
        dlg.setContentView(R.layout.chgpassword_frag);
        dlg.setTitle("Change Password");
        chgpassfr = (Button) dlg.findViewById(R.id.chgbtn);

        chgpassfr.setOnClickListener(this::onChgPassFrClick);
        cancel = (Button) dlg.findViewById(R.id.cancelbtn);
        cancel.setOnClickListener(view -> dlg.dismiss());
        dlg.show();
    }

    protected void onChgPassFrClick(View view) {
        oldpass = (EditText) dlg.findViewById(R.id.oldpass);
        newpass = (EditText) dlg.findViewById(R.id.newpass);
        oldpasstxt = oldpass.getText().toString();
        newpasstxt = newpass.getText().toString();

        App.get().getUserApi().changePassword(new ChgPassRequest(token, oldpasstxt, newpasstxt))
                .compose(App.toastError())
                .compose(App.applySchedulers())
                .subscribe(resp -> {
                    if (resp.isRes()) dlg.dismiss();
                    Toast.makeText(getApplication(), resp.getResponse(), Toast.LENGTH_SHORT).show();
                });
    }

}
