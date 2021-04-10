package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingsActivity extends AppCompatActivity {
    private Button resourcesBtn;
    private Button homeBtn;
    private Button aboutBtn;

    public String name;
    public String firstContactName;
    public String secondContactName;
    public String firstContactNum;
    public String secondContactNum;

    public String name_load;
    public String firstContactName_load;
    public String secondContactName_load;
    public String firstContactNum_load;
    public String secondContactNum_load;

    private EditText nameInput;
    private EditText firstContactNameInput;
    private EditText secondContactNameInput;
    private EditText firstContactNumInput;
    private EditText secondContactNumInput;

    SharedPreferences sp;

    private Button saveAllBtn;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String NAME = "name";
    public static final String FIRST_CONT_NAME = "firstContactName";
    public static final String FIRST_CONT_NUM = "firstContactNum";
    public static final String SEC_CONT_NAME = "secondContactName";
    public static final String SEC_CONT_NUM = "secondContactNum";




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

        saveAllBtn =(Button) findViewById(R.id.saveAll);

        sp = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        saveAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = nameInput.getText().toString();
                firstContactName = firstContactNameInput.getText().toString();
                firstContactNum = firstContactNumInput.getText().toString();
                secondContactName = secondContactNameInput.getText().toString();
                secondContactNum = secondContactNumInput.getText().toString();
                saveData();

                sendData();

            }
        });

        loadData();
        updateViews();


    }
    public void saveData(){

        if(!validateNumber1(firstContactNumInput.getText().toString())){
            firstContactNumInput.setError("Please enter a valid phone number");
            return;
        }
        if(!validateNumber1(secondContactNumInput.getText().toString())){
            secondContactNumInput.setError("Please enter a valid phone number");
            return;
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(NAME, name);
        editor.putString(FIRST_CONT_NAME, firstContactName);
        editor.putString(SEC_CONT_NAME, secondContactName);
        editor.putString(FIRST_CONT_NUM, firstContactNum);
        editor.putString(SEC_CONT_NUM, secondContactNum);
        editor.commit();

        Toast.makeText(SettingsActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData(){

        name_load = sp.getString(NAME, "");
        firstContactName_load = sp.getString(FIRST_CONT_NAME, "");
        firstContactNum_load = sp.getString(FIRST_CONT_NUM, "");
        secondContactName_load = sp.getString(SEC_CONT_NAME, "");
        secondContactNum_load = sp.getString(SEC_CONT_NUM, "");
    }

    public void  updateViews(){
        nameInput.setText(name_load);
        firstContactNameInput.setText(firstContactName_load);
        firstContactNumInput.setText(firstContactNum_load);
        secondContactNameInput.setText(secondContactName_load);
        secondContactNumInput.setText(secondContactNum_load);


    }
    public void sendData(){


        Intent i = new Intent(SettingsActivity.this, MainActivity.class);

        i.putExtra(MainActivity.FIRST_NAME, firstContactName);
        i.putExtra(MainActivity.FIRST_NUM, firstContactNum);
        i.putExtra(MainActivity.SECOND_NAME, secondContactName);
        i.putExtra(MainActivity.SECOND_NUM, firstContactNum);

    }

    private boolean validateNumber1(String input){
        Pattern p = Pattern.compile("[2-9][0-9]{9}");
        Matcher m = p.matcher((input));
        return m.matches();




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