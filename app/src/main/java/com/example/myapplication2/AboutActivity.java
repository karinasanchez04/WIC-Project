package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity {
    private Button settingsBtn;
    private Button homeBtn;
    private Button resourcesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_screen);

        //functionality of buttons on toolbar
        settingsBtn = (Button) findViewById(R.id.button5);
        settingsBtn.setOnClickListener(v -> openSettingsActivity()) ;

        homeBtn = (Button) findViewById(R.id.button4);
        homeBtn.setOnClickListener(v -> openHomeActivity());

        resourcesBtn = (Button) findViewById(R.id.button3);
        resourcesBtn.setOnClickListener(v -> openResourcesActivity());


    }

    public void openHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void openResourcesActivity() {
        Intent intent = new Intent(this, ResourcesActivity.class);
        startActivity(intent);
    }

}