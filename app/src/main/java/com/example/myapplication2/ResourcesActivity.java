package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.Button;
import android.widget.TextView;

public class ResourcesActivity extends AppCompatActivity {
    private Button settingsBtn;
    private Button homeBtn;
    private Button aboutBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resources_screen);

        //to have the link working for care
        TextView textCare = (TextView) findViewById(R.id.textView8);
        textCare.setMovementMethod(LinkMovementMethod.getInstance());

        //caps link
        TextView textCaps = (TextView) findViewById(R.id.textView10);
        textCaps.setMovementMethod(LinkMovementMethod.getInstance());

        //functionality of buttons on toolbar
        settingsBtn = (Button) findViewById(R.id.button9);
        settingsBtn.setOnClickListener(v -> openSettingsActivity()) ;

        homeBtn = (Button) findViewById(R.id.button16);
        homeBtn.setOnClickListener(v -> openHomeActivity());

        aboutBtn = (Button) findViewById(R.id.button17);
        aboutBtn.setOnClickListener(v -> openAboutActivity());


    }

    public void openHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void openAboutActivity() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

}