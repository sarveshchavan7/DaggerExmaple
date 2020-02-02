package com.example.daggerexamples.ui.main;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.daggerexamples.BaseActivity;
import com.example.daggerexamples.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "MAIN ACTIVITY", Toast.LENGTH_SHORT).show();
    }
}
