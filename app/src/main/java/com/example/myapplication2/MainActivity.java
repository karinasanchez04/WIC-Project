package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ComponentActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button resourcesBtn;
    private Button settingsBtn;
    private Button aboutBtn;
    private Button emergencyBtn;

    public static final String FIRST_NAME = "FIRST CONTACT NAME";
    public static final String SECOND_NAME = "SECOND CONTACT NAME";
    public static final String FIRST_NUM = "FIRST CONTACT NUM";
    public static final String SECOND_NUM = "SECOND CONTACT NUM";

    private String firstContactName;
    private String firstContactNum;
    private String secondContactName;
    private String secondContactNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences sp = getApplicationContext().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);

        //stores contact info
        firstContactName = sp.getString("firstContactName", "");
        secondContactName = sp.getString("secondContactName", "");
        firstContactNum = sp.getString("firstContactNum", "");
        secondContactNum = sp.getString("secondContactNum", "");


        //functionality of buttons on toolbar
        resourcesBtn = findViewById(R.id.button12);
        resourcesBtn.setOnClickListener(v -> openResourcesActivity()) ;

        settingsBtn = (Button) findViewById(R.id.button13);
        settingsBtn.setOnClickListener(v -> openSettingsActivity()) ;

        aboutBtn = (Button) findViewById(R.id.button14);
        aboutBtn.setOnClickListener(v -> openAboutActivity()) ;

        //emergencyBtn functionality
        emergencyBtn = (Button) findViewById(R.id.button2);
        emergencyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:911"));
                startActivity(intent);
            }
        });
        promptUser();
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

    public void promptUser(){
        if(firstContactNum.isEmpty() && secondContactNum.isEmpty()){
            new AlertDialog.Builder(this)
                    .setTitle("Contact Info not set")
                    .setMessage("Please enter contact information in Settings for Share Location feature to work")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        }
    }
}