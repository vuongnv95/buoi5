package com.example.demo_buoi_5.service.start_service;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demo_buoi_5.R;

public class ExampleStartActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_example_start);
        Intent intent = new Intent(this, ExampleStartService.class);
        startService(intent);
    }
}
