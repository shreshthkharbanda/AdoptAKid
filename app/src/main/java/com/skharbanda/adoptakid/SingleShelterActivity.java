package com.skharbanda.adoptakid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#05386B"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>AdoptAKid </font>"));

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

        shelterContact.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Contact Shelter");
            builder.setIcon(R.drawable.logo);
            builder.setMessage("How would you like to contact the shelter?");
            builder.setPositiveButton("Call",
                    (dialog, id) -> {
                        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                        phoneIntent.setData(Uri.parse("tel:" + phoneText.getText().toString()));
                    });

            builder.setNeutralButton("Cancel",
                    (dialog, id) -> dialog.cancel());

            builder.setNegativeButton("Email",
                    (dialog, id) -> {
                        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{
                                emailText.getText().toString()
                        });
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Adopt A Kid");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "");

                        emailIntent.setType("message/rfc822");

                        try {
                            startActivity(Intent.createChooser(emailIntent,
                                    "Send email using..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(getApplicationContext(),
                                    "No email clients installed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
            builder.create().show();
        });
    }
}
