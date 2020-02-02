package com.example.daggerexamples.ui.auth;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.RequestManager;
import com.example.daggerexamples.R;
import com.example.daggerexamples.model.Users;
import com.example.daggerexamples.ui.main.MainActivity;
import com.example.daggerexamples.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {
    private static final String TAG = "AuthActivity";

    private EditText edtUsersId;

    private AuthViewModel authViewModel;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        edtUsersId = findViewById(R.id.user_id_input);
        progressBar = findViewById(R.id.progress_bar);
        findViewById(R.id.login_button).setOnClickListener(this);

        authViewModel = ViewModelProviders.of(this, providerFactory).get(AuthViewModel.class);
        setLogo();

        subscribeObservers();
    }

    private void subscribeObservers() {
        authViewModel.observeAuthState().observe(this, new Observer<AuthResource<Users>>() {
            @Override
            public void onChanged(AuthResource<Users> usersAuthResource) {
                if (usersAuthResource != null) {
                    switch (usersAuthResource.status) {
                        case LOADING:
                            showProgressBar(true);
                            break;
                        case AUTHENTICATED:
                            showProgressBar(false);
                            onLoginSuccess();
                            break;
                        case ERROR:
                            showProgressBar(false);
                            Toast.makeText(AuthActivity.this, usersAuthResource.message + "1-10", Toast.LENGTH_SHORT).show();
                            break;
                        case NOT_AUTHENTICATED:
                            showProgressBar(false);
                            break;
                    }
                }
            }
        });
    }

    private void onLoginSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setLogo() {
        requestManager
                .load(logo)
                .into((ImageView) findViewById(R.id.login_logo));
    }

    private void showProgressBar(boolean isVisible) {
        if (isVisible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                attemptLogin();
                break;
        }
    }

    private void attemptLogin() {
        Log.d(TAG, "attemptLogin");
        Toast.makeText(this, "attemptLogin", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(edtUsersId.getText().toString())) {
            return;
        }
        authViewModel.authenticateWithId(Integer.parseInt(edtUsersId.getText().toString()));
    }
}
