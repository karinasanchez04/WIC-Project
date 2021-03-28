package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button resourcesBtn;
    private Button settingsBtn;
    private Button aboutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //functionality of buttons on toolbar
        resourcesBtn = findViewById(R.id.button12);
        resourcesBtn.setOnClickListener(v -> openResourcesActivity()) ;

        settingsBtn = (Button) findViewById(R.id.button13);
        settingsBtn.setOnClickListener(v -> openSettingsActivity()) ;

        aboutBtn = (Button) findViewById(R.id.button14);
        aboutBtn.setOnClickListener(v -> openAboutActivity()) ;


    }
    public void openSettingsActivity(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void openResourcesActivity(){
        Intent intent = new Intent(this, ResourcesActivity.class);
        startActivity(intent);
    }

    public void openAboutActivity(){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
}