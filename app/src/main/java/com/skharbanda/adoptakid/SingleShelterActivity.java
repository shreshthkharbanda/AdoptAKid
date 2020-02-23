package com.skharbanda.adoptakid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleShelterActivity extends AppCompatActivity {


    ImageView imageView;
    TextView nameText;
    TextView addressText;
    TextView hoursText;
    TextView descriptionText;
    TextView websiteText;
    TextView phoneText;
    TextView emailText;
    Button shelterContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_shelter);

        imageView = findViewById(R.id.childLogo);
        nameText = findViewById(R.id.nameText);
        addressText = findViewById(R.id.addressText);
        hoursText = findViewById(R.id.shelterHours);
        descriptionText = findViewById(R.id.descriptionText);
        websiteText = findViewById(R.id.websiteText);
        phoneText = findViewById(R.id.phoneNumberText);
        emailText = findViewById(R.id.emailText);
        shelterContact = findViewById(R.id.shelterContactText);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String[] values = extras.getStringArray("key");
            //The key argument here must match that used in the other activity
            byte[] b = extras.getByteArray("picture");
            Bitmap bmp = null;
            if (b != null) {
                bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
                imageView.setImageBitmap(bmp);
            }

            nameText.setText(values[0]);
            addressText.setText(values[1]);
            hoursText.setText(values[2]);
            descriptionText.setText(values[3]);
            websiteText.setText(values[4]);
            phoneText.setText(values[5]);
            emailText.setText(values[6]);
        }
        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#bac3d4"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>AdoptAKid </font>"));
        getSupportActionBar().setElevation(200);

        shelterContact.setOnClickListener(v -> {

        });
    }
}
