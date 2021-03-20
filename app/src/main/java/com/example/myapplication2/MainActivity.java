package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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
    }
}