package com.amal.nodelogin.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amal.nodelogin.App;
import com.amal.nodelogin.R;
import com.amal.nodelogin.model.server.request.Credentials;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RegisterActivity extends AppCompatActivity {
    EditText email, password;
    Button login, register;
    String emailtxt, passwordtxt;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo);
        setTitle(null);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.registerbtn);
        login = (Button) findViewById(R.id.login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
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
