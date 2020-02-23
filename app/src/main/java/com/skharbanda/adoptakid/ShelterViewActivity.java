package com.skharbanda.adoptakid;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ShelterViewActivity extends AppCompatActivity{
    int[] images = {R.drawable.face_icon, R.drawable.face_icon, R.drawable.face_icon, R.drawable.face_icon, R.drawable.face_icon, R.drawable.face_icon,R.drawable.face_icon};

    String[] name = {"Shelter 1", "Shelter 2", "Shelter 3", "Shelter 4", "Shelter 5", "Shelter 6", "Shelter 7"};

    String[] age = {"643 South Indian Summer Court\n" +
            "Royal Oak, MI 48067", "652 Locust Drive\n" +
            "Wellington, FL 33414", "626 Lancaster Ave.\n" +
            "Mount Juliet, TN 37122", "426 2nd St.\n" +
            "York, PA 17402", "2 Canal Drive\n" +
            "Smithtown, NY 11787", "246 Pineknoll Drive\n" +
            "Minneapolis, MN 55406", "564 Lafayette St.\n" +
            "Palos Verdes Peninsula, CA 90274"};

    ListView lView;

    CustomLvAdapter lAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lView = (ListView) findViewById(R.id.listview);

        lAdapter = new CustomLvAdapter(ShelterViewActivity.this, name, age, images);

        lView.setAdapter(lAdapter);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(ShelterViewActivity.this, name[i]+" "+ age[i], Toast.LENGTH_SHORT).show();

            }
        });

    }
}
