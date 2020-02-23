package com.skharbanda.adoptakid;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    int[] images = {R.drawable.face_icon, R.drawable.face_icon, R.drawable.face_icon, R.drawable.face_icon, R.drawable.face_icon, R.drawable.face_icon,R.drawable.face_icon, R.drawable.face_icon, R.drawable.face_icon, R.drawable.face_icon,R.drawable.face_icon};

    String[] name = {"Joe", "Bob", "Sam", "Jack", "John", "Ryan", "Michel", "Jack", "John", "Ryan", "Michel"};

    String[] age = {"10", "14", "15", "16", "13", "7", "12", "16", "13", "7", "12"};

    ListView lView;

    CustomLvAdapter lAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lView = (ListView) findViewById(R.id.listview);

        lAdapter = new CustomLvAdapter(MainActivity.this, name, age, images);

        lView.setAdapter(lAdapter);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(MainActivity.this, name[i]+" "+ age[i], Toast.LENGTH_SHORT).show();

            }
        });

    }
}