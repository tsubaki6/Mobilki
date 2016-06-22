package com.amal.nodelogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class RegisterActivity extends Activity {
    EditText email, password;
    Button login, register;
    String emailtxt, passwordtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.registerbtn);
        login = (Button) findViewById(R.id.login);
    }

    @OnClick(R.id.login)
    public void onLoginClick() {
        Intent regactivity = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(regactivity);
        finish();
    }


    @OnClick(R.id.registerbtn)
    public void onRegisterClick() {
        emailtxt = email.getText().toString();
        passwordtxt = password.getText().toString();
        App.get().getUserApi().register(new Credentials(emailtxt, passwordtxt))
                .compose(App.toastError())
                .compose(App.applySchedulers())
                .subscribe(resp -> {
                    String response = resp.getResponse();
                    Toast.makeText(getApplication(), response, Toast.LENGTH_LONG).show();
                    Log.d("Hello", response);
                });
    }

}
