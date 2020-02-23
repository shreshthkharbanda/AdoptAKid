package com.skharbanda.adoptakid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
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
            ageText.setText(values[1] +" years old");
            shelterText.setText("Shelter: " + values[10]);
            genderText.setText("Bio: " + values[3]);
            raceText.setText("Disabilities: " + values[4]);
            weightText.setText("Gender: " + values[5]);
            heightText.setText("Height: " + values[6]);
            allergiesText.setText("Weight: " + values[7]);
            medicalHistory.setText("Medical History: " + values[8]);
            disabilitiesText.setText("Race: " + values[9]);
            bioText.setText("Allergies: " + values[2]);
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

        shelterContact.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.contact_shelter_dialog);
                dialog.setTitle("Title...");

                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }
}
