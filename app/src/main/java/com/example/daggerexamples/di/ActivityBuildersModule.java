package com.example.daggerexamples.di;

import com.example.daggerexamples.di.auth.AuthViewModelModule;
import com.example.daggerexamples.ui.auth.AuthActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = {AuthViewModelModule.class}
    )
    abstract AuthActivity contributeAndroidInjector();


}
