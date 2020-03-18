package com.skharbanda.adoptakid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    Spinner mSpinner;
    EditText first;
    EditText last;
    EditText email;
    EditText pass;
    EditText phone;
    EditText address;
    Button signUp;
    Button logIn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mSpinner = findViewById(R.id.signUpGender);
        first = findViewById(R.id.signUpFirst);
        last = findViewById(R.id.signUpLast);
        email = findViewById(R.id.signUpEmail);
        pass = findViewById(R.id.signUpPwd);
        phone = findViewById(R.id.signUpPhone);
        address = findViewById(R.id.signUpAddress);
        signUp = findViewById(R.id.signUpBtn);
        logIn = findViewById(R.id.loginHereBtn);
        progressBar = findViewById(R.id.signUpProgress);

        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
            }
        });

        logIn.setOnClickListener(view -> startActivity(new Intent(SignupActivity.this, LoginActivity.class)));
        signUp.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            if ((first.getText().toString().isEmpty() || last.getText().toString().isEmpty() ||
                    address.getText().toString().isEmpty() || email.getText().toString().isEmpty() ||
                    pass.getText().toString().isEmpty()) || phone.length() != 10 ||
                    !email.getText().toString().contains("@")) {
                Toast.makeText(this, "Please fill out all fields with valid input.", Toast.LENGTH_LONG).show();
            } else {
                Map<String, Object> user = new HashMap<>();
                user.put("first name", first.getText().toString());
                user.put("last name", last.getText().toString());
                user.put("address", address.getText().toString());
                user.put("gender", mSpinner.getSelectedItem().toString());
                user.put("phone", phone.getText().toString());
                user.put("email", email.getText().toString());
                user.put("password", pass.getText().toString());

                db.collection("users").document(email.getText().toString())
                        .set(user)
                        .addOnSuccessListener(aVoid -> System.out.println("Signed up"))
                        .addOnFailureListener(aVoid -> System.out.println("Not Signed up"));
                progressBar.setVisibility(View.GONE);
                startActivity(new Intent(SignupActivity.this, MainActivity.class));
            }
        });
    }
}
