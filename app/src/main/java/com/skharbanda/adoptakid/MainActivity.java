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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;
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
    List<String> allergies = new ArrayList<>();
    List<String> bios = new ArrayList<>();
    List<String> disabilities = new ArrayList<>();
    List<String> genders = new ArrayList<>();
    List<String> heights = new ArrayList<>();
    List<String> weights = new ArrayList<>();
    List<String> medicalHistories = new ArrayList<>();
    List<String> races = new ArrayList<>();
    List<String> shelter = new ArrayList<>();

    ListView lView;

    CustomLvAdapter lAdapter;

    Button footerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lView = findViewById(R.id.listview);
        footerButton = findViewById(R.id.footerButton);

        loadData();

        lView.setOnItemClickListener((adapterView, view, i, l) -> {
//                Toast.makeText(MainActivity.this, names.get(i) + " " + ages.get(i), Toast.LENGTH_SHORT).show();
//                String value="Hello world";
            Intent intent = new Intent(MainActivity.this, KidInfoActivity.class);
            intent.putExtra("key", new String[]{names.get(i), ages.get(i),
                    allergies.get(i), bios.get(i), disabilities.get(i),
                    genders.get(i), heights.get(i), weights.get(i),
                    medicalHistories.get(i), races.get(i), shelter.get(i),
            });
            Bitmap bitmap = ((BitmapDrawable)images.get(i)).getBitmap();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//                bitmap = getResizedBitmap(bitmap, 10);
            byte[] b = baos.toByteArray();
            intent.putExtra("picture", b);
            startActivity(intent);
        });
        footerButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ShelterViewActivity.class);
            startActivity(intent);
        });
    }

    private void loadData() {

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
                                URL url = null;
                                try {
                                    String name = document.getString("name");
                                    String age = document.getString("age");
                                    String imageUrl = document.getString("image");
                                    String allergy = document.getString("allergies");
                                    String bio = document.getString("bio");
                                    String disability = document.getString("disabilities");
                                    String gender = document.getString("gender");
                                    String height = document.getString("height");
                                    String weight = document.getString("weight");
                                    String medicalHistory = document.getString("medical history");
                                    String race = document.getString("race");
                                    String kidShelter = document.getString("shelter");

                                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                    StrictMode.setThreadPolicy(policy);

                                    url = new URL(imageUrl);
                                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                    Drawable d = new BitmapDrawable(getResources(), bmp);

                                    names.add(name);
                                    ages.add(age);
                                    images.add(d);
                                    allergies.add(allergy);
                                    bios.add(bio);
                                    disabilities.add(disability);
                                    genders.add(gender);
                                    heights.add(height);
                                    weights.add(weight);
                                    medicalHistories.add(medicalHistory);
                                    races.add(race);
                                    shelter.add(kidShelter);

                                    lAdapter = new CustomLvAdapter(MainActivity.this,
                                            names.toArray(new String[names.size()]),
                                            ages.toArray(new String[ages.size()]),
                                            images.toArray(new Drawable[images.size()]),
                                            allergies.toArray(new String[allergies.size()]),
                                            bios.toArray(new String[bios.size()]),
                                            disabilities.toArray(new String[disabilities.size()]),
                                            genders.toArray(new String[genders.size()]),
                                            heights.toArray(new String[heights.size()]),
                                            weights.toArray(new String[weights.size()]),
                                            medicalHistories.toArray(new String[medicalHistories.size()]),
                                            races.toArray(new String[races.size()]),
                                            shelter.toArray(new String[shelter.size()]));

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
    }
}