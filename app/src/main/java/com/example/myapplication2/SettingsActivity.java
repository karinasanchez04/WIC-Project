package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {
    private Button resourcesBtn;
    private Button homeBtn;
    private Button aboutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);

        //functionality of buttons on toolbar
        resourcesBtn = findViewById(R.id.button11);
        resourcesBtn.setOnClickListener(v -> openResourcesActivity());

        homeBtn = (Button) findViewById(R.id.button10);
        homeBtn.setOnClickListener(v -> openHomeActivity());

        aboutBtn = (Button) findViewById(R.id.button15);
        aboutBtn.setOnClickListener(v -> openAboutActivity());


    }

    public void openHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openResourcesActivity() {
        Intent intent = new Intent(this, ResourcesActivity.class);
        startActivity(intent);
    }

    public void openAboutActivity() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

}