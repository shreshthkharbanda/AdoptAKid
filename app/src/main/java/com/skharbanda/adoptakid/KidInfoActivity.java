package com.skharbanda.adoptakid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;

public class KidInfoActivity extends AppCompatActivity {

    ImageView imageView;
    TextView nameText;
    TextView ageText;
    TextView shelterText;
    TextView genderText;
    TextView raceText;
    TextView weightText;
    TextView heightText;
    TextView allergiesText;
    TextView medicalHistory;
    TextView disabilitiesText;
    TextView bioText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kid_info);

        imageView = findViewById(R.id.imageView);
        nameText = findViewById(R.id.nameText);
        ageText = findViewById(R.id.ageText);
        shelterText = findViewById(R.id.shelterText);
        genderText = findViewById(R.id.genderText);
        raceText = findViewById(R.id.raceText);
        weightText = findViewById(R.id.weightText);
        heightText = findViewById(R.id.heightText);
        allergiesText = findViewById(R.id.allergiesText);
        medicalHistory = findViewById(R.id.medHistoryText);
        disabilitiesText = findViewById(R.id.disabilitiesText);
        bioText = findViewById(R.id.bioText);

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

            if (values != null) {
                nameText.setText(values[0]);
            }
            ageText.setText(values[1]);
            shelterText.setText(values[2]);
            genderText.setText(values[3]);
            raceText.setText(values[4]);
            weightText.setText(values[5]);
            heightText.setText(values[6]);
            allergiesText.setText(values[7]);
            medicalHistory.setText(values[8]);
            disabilitiesText.setText(values[9]);
            bioText.setText(values[10]);
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
    }
}
