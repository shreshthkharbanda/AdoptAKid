package com.skharbanda.adoptakid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;

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
    Button shelterContact;
    String email = "";
    String phone = "";

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kid_info);

        imageView = findViewById(R.id.childLogo);
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

            if (values != null) {
                nameText.setText(values[0]);
            }
            ageText.setText(values[1] + " years old");
            shelterText.setText("Shelter: " + values[10]);
            genderText.setText("Bio: " + values[3]);
            raceText.setText("Disabilities: " + values[4]);
            weightText.setText("Gender: " + values[5]);
            heightText.setText("Height: " + values[6]);
            allergiesText.setText("Weight: " + values[7]);
            medicalHistory.setText("Medical History: " + values[8]);
            disabilitiesText.setText("Race: " + values[9]);
            bioText.setText("Allergies: " + values[2]);
            email = values[11];
            phone = values[12];
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

        shelterContact.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Contact Shelter");
            builder.setIcon(R.drawable.logo);
            builder.setMessage("How would you like to contact the shelter?");
            builder.setPositiveButton("Call",
                    (dialog, id) -> {
                        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                        phoneIntent.setData(Uri.parse("tel:" + phone));
                    });

            builder.setNeutralButton("Cancel",
                    (dialog, id) -> dialog.cancel());

            builder.setNegativeButton("Email",
                    (dialog, id) -> {
                        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{
                                email
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
