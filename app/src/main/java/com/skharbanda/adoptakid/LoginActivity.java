package com.skharbanda.adoptakid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText pass;
    Button logIn;
    Button signup;
    Button guest;
    ProgressBar progressBar;
    boolean loggedIn = true;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();


        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        email = findViewById(R.id.loginUserName);
        pass = findViewById(R.id.loginPwd);
        logIn = findViewById(R.id.loginBtn);
        signup = findViewById(R.id.signUpBtn);
        guest = findViewById(R.id.guestBtn);
        progressBar = findViewById(R.id.loginProgress);
        relativeLayout = findViewById(R.id.relativeLayout);

        logIn.setOnClickListener(view -> {
            relativeLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            db.collection("users").document(email.getText().toString())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String password = String.valueOf(document.get("password"));
                                if(password.equals(pass.getText().toString())) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                    Toast.makeText(this, "Logged In Successfully.", Toast.LENGTH_SHORT).show();
                                    loggedIn = true;
                                    startActivity(intent);
                                    relativeLayout.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                } else {
                                    relativeLayout.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                relativeLayout.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(this, "No account with this email.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            relativeLayout.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(this, "Error. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        signup.setOnClickListener(view -> {
           startActivity(new Intent(this, SignupActivity.class));
        });
        guest.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            loggedIn = true;
            startActivity(intent);
        });
    }
}
