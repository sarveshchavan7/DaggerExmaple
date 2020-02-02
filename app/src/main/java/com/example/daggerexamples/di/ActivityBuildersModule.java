package com.example.daggerexamples.di;

import com.example.daggerexamples.di.auth.AuthModule;
import com.example.daggerexamples.di.auth.AuthViewModelModule;
import com.example.daggerexamples.ui.auth.AuthActivity;
import com.example.daggerexamples.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = {AuthViewModelModule.class, AuthModule.class}
    )
    abstract AuthActivity contributeAuthActivity();

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

}
