package com.skharbanda.adoptakid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    List<Drawable> images = new ArrayList<>();
    List<String> names = new ArrayList<>();
    List<String> ages = new ArrayList<>();

    ListView lView;

    CustomLvAdapter lAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lView = findViewById(R.id.listview);

        // Render Testing Button
        Button buttonOne = findViewById(R.id.footerButton2);
        buttonOne.setOnClickListener(v -> {
            System.out.println("Button Clicked");

            Intent activity2Intent = new Intent(getApplicationContext(), KidInfoActivity.class);
            startActivity(activity2Intent);
        });


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
        getSupportActionBar().setElevation(20);

        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("kids")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.d("QueryDocumentSnapshot", document.getId() + " => " + document.getData());
                                URL url = null;
                                try {
                                    String name = document.getString("name");
                                    String age = document.getString("age");
                                    String imageUrl = document.getString("image");
                                    /*Toast.makeText(getApplicationContext(), "Name: " + name, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(), "Age: " + age, Toast.LENGTH_SHORT).show();*/

                                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                    StrictMode.setThreadPolicy(policy);

                                    url = new URL(imageUrl);
                                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                    Drawable d = new BitmapDrawable(getResources(), bmp);

                                    names.add(name);
                                    ages.add(age);
                                    images.add(d);
                                    /*Toast.makeText(MainActivity.this, "Names 1 = " + names.toString(), Toast.LENGTH_SHORT).show();*/


                                    String[] namesArr = new String[names.size()];
                                    for (int i = 0; i < names.size(); i++) {
                                        namesArr[i] = names.get(i);
                                    }
                                    String[] agesArr = new String[ages.size()];
                                    for (int i = 0; i < ages.size(); i++) {
                                        agesArr[i] = ages.get(i);
                                    }
                                    Drawable[] imagesArr = new Drawable[images.size()];
                                    for (int i = 0; i < images.size(); i++) {
                                        imagesArr[i] = images.get(i);
                                    }

                                    lAdapter = new CustomLvAdapter(MainActivity.this, namesArr, agesArr, imagesArr);
                                    /*Toast.makeText(MainActivity.this, "Names 2= " + namesArr, Toast.LENGTH_LONG).show();
                                    Toast.makeText(MainActivity.this, "Ages 2= " + agesArr, Toast.LENGTH_LONG).show();
                                    Toast.makeText(MainActivity.this, "Images 2= " + imagesArr, Toast.LENGTH_LONG).show();*/

                                    lView.setAdapter(lAdapter);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Log.d("QueryDocumentSnapshot", "Error getting documents: ", task.getException());
                        }
                    }
                });

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, names.get(i) + " " + ages.get(i), Toast.LENGTH_SHORT).show();
            }
        });
    }
}