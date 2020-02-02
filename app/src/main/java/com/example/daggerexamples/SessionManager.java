package com.example.daggerexamples;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.daggerexamples.model.Users;
import com.example.daggerexamples.ui.auth.AuthResource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionManager {

    private static final String TAG = "SessionManager";

    private MediatorLiveData<AuthResource<Users>> cachedUser = new MediatorLiveData<>();

    @Inject
    public SessionManager() {
    }

    public void authenticateWithId(final LiveData<AuthResource<Users>> source) {
        if (cachedUser != null) {
            cachedUser.setValue(AuthResource.loading((Users) null));
            cachedUser.addSource(source, new Observer<AuthResource<Users>>() {
                @Override
                public void onChanged(AuthResource<Users> usersAuthResource) {
                    cachedUser.setValue(usersAuthResource);
                    cachedUser.removeSource(source);
                }
            });
        }
    }

    private void logOut(){
        Log.d(TAG,"LOG OUT");
        cachedUser.setValue(AuthResource.<Users>logout());
    }

    public LiveData<AuthResource<Users>> getAuthUser(){
        return cachedUser;
    }
}
