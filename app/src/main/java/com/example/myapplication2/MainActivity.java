package com.example.myapplication2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ComponentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.provider.MediaStore;
import android.widget.VideoView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button resourcesBtn;
    private Button settingsBtn;
    private Button aboutBtn;
    private Button emergencyBtn;
    //variable to start youtube livestream
    private Button startLive;

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

        //adding on click listener to record button
        findViewById(R.id.record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult(intent, 1);
            }
        });

        //adding on click listener to livestream button
        startLive = findViewById(R.id.livestream);
        startLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calls method to check if device can support youtube livestreaming
                validateMobileLiveIntent(MainActivity.this);
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            VideoView videoView = new VideoView(this);
            videoView.setVideoURI(data.getData());
            videoView.start();
            builder.setView(videoView).show();
        }

    }

    private boolean canResolveMobileLiveIntent(Context context) {
        Intent intent = new Intent("com.google.android.youtube.intent.action.CREATE_LIVE_STREAM").setPackage("com.google.android.youtube");
        PackageManager pm = context.getPackageManager();
        List resolveInfo = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        return resolveInfo != null && !resolveInfo.isEmpty();
    }

    private void validateMobileLiveIntent(Context context) {
        if (canResolveMobileLiveIntent(context)) {
            startMobileLive(MainActivity.this);
        } else {
            Toast.makeText(context, "Please update your Youtube app", Toast.LENGTH_SHORT).show();
        }
    }

    private Intent createMobileLiveIntent(Context context, String description) {
        Intent intent = new Intent("com.google.android.youtube.intent.action.CREATE_LIVE_STREAM").setPackage("com.google.android.youtube");
        Uri referrer= new Uri.Builder()
                .scheme("android-app")
                .appendPath(context.getPackageName())
                .build();

        intent.putExtra(Intent.EXTRA_REFERRER, referrer);
        if (!TextUtils.isEmpty(description)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, description);
        }

        return intent;
    }

    private void startMobileLive(Context context) {

        Intent mobileLiveIntent = createMobileLiveIntent(context, "Streaming via ...");

        startActivity(mobileLiveIntent);
    }
}