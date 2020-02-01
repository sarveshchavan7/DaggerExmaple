package com.example.daggerexamples.di.auth;

import androidx.lifecycle.ViewModel;

import com.example.daggerexamples.di.ViewModelKey;
import com.example.daggerexamples.ui.auth.AuthViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

// Responsible for injecting auth view model inside it's auth activity
@Module
public abstract class AuthViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel authViewModel);
}
