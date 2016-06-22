package com.amal.nodelogin;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends Activity {
    EditText email, password, res_email, code, newpass;
    Button login, cont, cont_code, cancel, cancel1, register, forpass;
    String emailtxt, passwordtxt, email_res_txt, code_txt, npass_txt;
    SharedPreferences pref;
    Dialog reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.loginbtn);
        register = (Button) findViewById(R.id.register);
        forpass = (Button) findViewById(R.id.forgotpass);

        pref = getSharedPreferences("AppPref", MODE_PRIVATE);
    }


    @OnClick(R.id.loginbtn)
    public void onLoginClick() {
        emailtxt = email.getText().toString();
        passwordtxt = password.getText().toString();
        Credentials credentials = new Credentials(emailtxt, passwordtxt);
        App.get().getUserApi().login(credentials)
                .compose(App.toastError())
                .compose(App.applySchedulers())
                .subscribe(loginResponse -> {
                    if (loginResponse.isRes()) {
                        String token = loginResponse.getToken();
                        String grav = loginResponse.getGrav();
                        SharedPreferences.Editor edit = pref.edit();
                        //Storing Data using SharedPreferences
                        edit.putString("token", token);
                        edit.putString("grav", grav);
                        edit.commit();
                        Intent profactivity = new Intent(LoginActivity.this, MapsActivity.class);

                        startActivity(profactivity);
                        finish();
                    }
                    Toast.makeText(getApplication(), loginResponse.getResponse(), Toast.LENGTH_LONG).show();
                });
    }

    @OnClick(R.id.register)
    public void onRegisterClick() {
        Intent regactivity = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(regactivity);
        finish();
    }

    @OnClick(R.id.forgotpass)
    public void onForgotPassClick() {
        reset = new Dialog(LoginActivity.this);
        reset.setTitle("Reset Password");
        reset.setContentView(R.layout.reset_pass_init);
        cont = (Button) reset.findViewById(R.id.resbtn);
        cancel = (Button) reset.findViewById(R.id.cancelbtn);
        cancel.setOnClickListener(view -> reset.dismiss());
        res_email = (EditText) reset.findViewById(R.id.email);

        cont.setOnClickListener(this::onForgotContClick);


        reset.show();
    }

    protected void onForgotContClick(View view) {
        email_res_txt = res_email.getText().toString();
        App.get().getUserApi().resetPassword(Email.from(email_res_txt))
                .compose(App.toastError())
                .compose(App.applySchedulers())
                .subscribe(resp -> {
                    if (resp.isRes()) {
                        Log.e("JSON", resp.getResponse() + " code: " + resp.getCode());
                        Toast.makeText(getApplication(), resp.getResponse(), Toast.LENGTH_LONG).show();
                        reset.setContentView(R.layout.reset_pass_code);
                        cont_code = (Button) reset.findViewById(R.id.conbtn);
                        code = (EditText) reset.findViewById(R.id.code);
                        newpass = (EditText) reset.findViewById(R.id.npass);
                        cancel1 = (Button) reset.findViewById(R.id.cancel);
                        cancel1.setOnClickListener(view1 -> reset.dismiss());
                        cont_code.setOnClickListener(this::onForgotContCodeClick);

                    } else {
                        Toast.makeText(getApplication(), resp.getResponse(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    protected void onForgotContCodeClick(View view) {
        code_txt = code.getText().toString();
        npass_txt = newpass.getText().toString();
        Log.e("Code", code_txt);
        Log.e("New pass", npass_txt);
        App.get().getUserApi().resetPasswordChg(new ResetPassChgRequest(email_res_txt, code_txt, npass_txt))
                .compose(App.toastError())
                .compose(App.applySchedulers())
                .subscribe(resp -> {
                    if (resp.isRes()) reset.dismiss();
                    Toast.makeText(getApplication(), resp.getResponse(), Toast.LENGTH_LONG).show();

                });
    }
}
