package com.skharbanda.adoptakid;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ShelterViewActivity extends AppCompatActivity {

    List<Drawable> images = new ArrayList<>();
    List<String> names = new ArrayList<>();
    List<String> addresses = new ArrayList<>();
    List<String> descriptions = new ArrayList<>();
    List<String> emails = new ArrayList<>();
    List<String> hours = new ArrayList<>();
    List<String> phones = new ArrayList<>();
    List<String> websites = new ArrayList<>();

    ListView lView;

    ShelterAdapter lAdapter;

    Button footerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_view);

        lView = findViewById(R.id.listview);
        footerButton = findViewById(R.id.footerButton);

        loadData();

        lView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(ShelterViewActivity.this, SingleShelterActivity.class);
            intent.putExtra("key", new String[]{names.get(i), addresses.get(i), hours.get(i),
                    descriptions.get(i), emails.get(i), phones.get(i), websites.get(i)
            });
            Bitmap bitmap = ((BitmapDrawable) images.get(i)).getBitmap();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//                bitmap = getResizedBitmap(bitmap, 10);
            byte[] b = baos.toByteArray();
            intent.putExtra("picture", b);
            startActivity(intent);
        });
        footerButton.setOnClickListener(v -> {
            Intent intent = new Intent(ShelterViewActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void loadData() {
        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("shelters")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            URL url;
                            try {
                                String name = document.getString("name");
                                String address = document.getString("address");
                                String description = document.getString("description");
                                String email = document.getString("email");
                                String hour = document.getString("hours");
                                String imageUrl = document.getString("image");
                                String phone = document.getString("phone");
                                String website = document.getString("website");

                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                StrictMode.setThreadPolicy(policy);

                                url = new URL(imageUrl);
                                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                Drawable d = new BitmapDrawable(getResources(), bmp);

                                names.add(name);
                                addresses.add(address);
                                images.add(d);
                                descriptions.add(description);
                                emails.add(email);
                                hours.add(hour);
                                phones.add(phone);
                                websites.add(website);

                                lAdapter = new ShelterAdapter(ShelterViewActivity.this,
                                        names.toArray(new String[names.size()]),
                                        addresses.toArray(new String[addresses.size()]),
                                        images.toArray(new Drawable[images.size()]),
                                        descriptions.toArray(new String[descriptions.size()]),
                                        emails.toArray(new String[emails.size()]),
                                        hours.toArray(new String[hours.size()]),
                                        phones.toArray(new String[phones.size()]),
                                        websites.toArray(new String[websites.size()]));

                                lView.setAdapter(lAdapter);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        Log.d("QueryDocumentSnapshot", "Error getting documents: ", task.getException());
                    }
                });
    }
}
