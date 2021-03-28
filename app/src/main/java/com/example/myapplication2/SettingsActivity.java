package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {
    private Button resourcesBtn;
    private Button homeBtn;
    private Button aboutBtn;

    private String name;
    private String firstContactName;
    private String secondContactName;
    private String firstContactNum;
    private String secondContactNum;

    private EditText nameInput;
    private EditText firstContactNameInput;
    private EditText secondContactNameInput;
    private EditText firstContactNumInput;
    private EditText secondContactNumInput;

    private Button saveNameBtn;
    private Button saveFirstConBtn;
    private Button saveSecConBtn;


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

        //to save user input
        nameInput = (EditText)findViewById(R.id.userNameInput);
        firstContactNameInput = (EditText)findViewById(R.id.firstContactName);
        secondContactNameInput = (EditText)findViewById(R.id.secondContactName);
        firstContactNumInput = (EditText)findViewById(R.id.firstContactNum);
        secondContactNumInput = (EditText)findViewById(R.id.secondContactNum);

        saveNameBtn = (Button) findViewById(R.id.saveNameBtn);
        saveNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameInput.getText().toString();

            }
        });
        saveFirstConBtn =(Button) findViewById(R.id.saveFirstContactBtn);
        saveFirstConBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstContactName = firstContactNameInput.getText().toString();
                firstContactNum = firstContactNumInput.getText().toString();


            }
        });
        saveSecConBtn = (Button) findViewById(R.id.saveSecondContactBtn);
        saveSecConBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                secondContactName = secondContactNameInput.getText().toString();
                secondContactNum = secondContactNumInput.getText().toString();

            }
        });
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