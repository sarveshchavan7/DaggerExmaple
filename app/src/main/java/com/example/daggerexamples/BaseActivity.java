package com.example.daggerexamples;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.example.daggerexamples.model.Users;
import com.example.daggerexamples.ui.auth.AuthActivity;
import com.example.daggerexamples.ui.auth.AuthResource;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Inject
    public SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscribeObserver();
    }

    private void subscribeObserver() {
        sessionManager.getAuthUser().observe(this, new Observer<AuthResource<Users>>() {
            @Override
            public void onChanged(AuthResource<Users> usersAuthResource) {
                switch (usersAuthResource.status) {
                    case LOADING:
                        break;
                    case AUTHENTICATED:
                        break;
                    case ERROR:
                        break;
                    case NOT_AUTHENTICATED:
                        Log.d(TAG, "NOT AUTHENTICATED");
                        break;
                }
            }
        });
    }

    private void navLoginScreen() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }
}
