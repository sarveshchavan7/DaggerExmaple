package com.example.daggerexamples.ui.auth;

import android.icu.lang.UScript;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.daggerexamples.SessionManager;
import com.example.daggerexamples.model.Users;
import com.example.daggerexamples.network.auth.AuthApi;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {
    private static final String TAG = "AuthViewModel";

    private final AuthApi authApi;

    SessionManager sessionManager;

    private MediatorLiveData<AuthResource<Users>> authUser = new MediatorLiveData<>();

    @Inject
    public AuthViewModel(AuthApi authApi, SessionManager sessionManager) {
        this.authApi = authApi;
        this.sessionManager = sessionManager;
    }

    public void authenticateWithId(final int userId) {
        Log.d(TAG, "ATTEMPTING TO LOGIN");
        sessionManager.authenticateWithId(queryWithUserId(userId));
    }

    private LiveData<AuthResource<Users>> queryWithUserId(int userId) {
        return LiveDataReactiveStreams.fromPublisher(
                authApi.getUser(userId)

                        .onErrorReturn(new Function<Throwable, Users>() {
                            @Override
                            public Users apply(Throwable throwable) throws Exception {
                                Users errorUsers = new Users();
                                errorUsers.setId(-1);
                                return errorUsers;
                            }
                        })

                        .map(new Function<Users, AuthResource<Users>>() {
                            @Override
                            public AuthResource<Users> apply(Users users) throws Exception {
                                if (users.getId() == -1) {
                                    return AuthResource.error("Cound not authenticate", (Users) null);
                                }
                                return AuthResource.authenticated(users);
                            }
                        })
                        .subscribeOn(Schedulers.io())
        );
    }

    public LiveData<AuthResource<Users>> observeAuthState() {
        return sessionManager.getAuthUser();
    }
}
