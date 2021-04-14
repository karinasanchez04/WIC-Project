package com.example.myapplication2;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Button resourcesBtn;
    private Button settingsBtn;
    private Button aboutBtn;
    private Button emergencyBtn;
    //variable to start youtube livestream
    private Button startLive;
    // location sharing
    private Button locationButton;

    public static final String FIRST_NAME = "FIRST CONTACT NAME";
    public static final String SECOND_NAME = "SECOND CONTACT NAME";
    public static final String FIRST_NUM = "FIRST CONTACT NUM";
    public static final String SECOND_NUM = "SECOND CONTACT NUM";

    private String firstContactName;
    private String firstContactNum;
    private String secondContactName;
    private String secondContactNum;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private String userLocation;
    Dialog dialog;
    // dialog text
    TextView dialogText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MediaPlayer fakecallAudioMP = MediaPlayer.create(this, R.raw.fakecallsound);

        Button FakeCall = (Button) this.<View>findViewById(R.id.button8);

        FakeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fakecallAudioMP.start();
            }
        });

        final MediaPlayer sirenAudioMP = MediaPlayer.create(this, R.raw.policesiren);

        Button Siren = (Button) this.<View>findViewById(R.id.button6);

        Siren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sirenAudioMP.start();
            }
        });


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        Log.d("tag", "onCreate: " + firebaseAuth.getCurrentUser().getEmail() + firebaseAuth.getCurrentUser().getDisplayName());

        SharedPreferences sp = getApplicationContext().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);

        //stores contact info
        firstContactName = sp.getString("firstContactName", "");
        secondContactName = sp.getString("secondContactName", "");
        firstContactNum = sp.getString("firstContactNum", "");
        secondContactNum = sp.getString("secondContactNum", "");


        //functionality of buttons on toolbar
        resourcesBtn = findViewById(R.id.button12);
        resourcesBtn.setOnClickListener(v -> openResourcesActivity());

        settingsBtn = (Button) findViewById(R.id.button13);
        settingsBtn.setOnClickListener(v -> openSettingsActivity());

        aboutBtn = (Button) findViewById(R.id.button14);
        aboutBtn.setOnClickListener(v -> openAboutActivity());

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

        // location sharing
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationButton = findViewById(R.id.location_button);

        // initialize dialog
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.location_dialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        // set up dialog buttons
        Button buttonShare = dialog.findViewById(R.id.button_share);
        Button buttonCancel = dialog.findViewById(R.id.button_cancel);

        // dialog textView
        dialogText = dialog.findViewById(R.id.textView_details);

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // share location with specified contacts in user settings
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        sendSMS();
                    } else {
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
                    }
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check permissions
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //dialog.show();
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });

    }

    public void openSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
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
        Uri referrer = new Uri.Builder()
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

    public void logout(final View view) {
        FirebaseAuth.getInstance().signOut();

        GoogleSignIn.getClient(this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build())
                .signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startActivity(new Intent(view.getContext(), Login.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Signout Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
                                location.getLongitude(), 1);
                        // set latitude and longitude on textview
                        Double latitude = addresses.get(0).getLatitude();
                        Double longitude = addresses.get(0).getLongitude();

                        userLocation = "Latitude: " + latitude + ", Longitude: " + longitude;

                        dialogText.setText(Html.fromHtml(
                                "<font color='#6200EE><b>Latitude: </b></font>" + latitude
                                        + "<font color='#6200EE><b>Longitude: </b></font>" + longitude
                                        + "<font color='#6200EE><b>Address: </b></font>"
                                        + addresses.get(0).getAddressLine(0)));
                        dialog.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void sendSMS() {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(firstContactNum, null, userLocation, null, null);
            smsManager.sendTextMessage(secondContactNum, null, userLocation, null, null);
            Toast.makeText(this, "Message sent.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to send message.", Toast.LENGTH_SHORT).show();
        }
    }
}


