package com.example.mapuslan.userUI.home;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mapuslan.R;

public class MaterialList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.points_list_view);

        ActionBar actionBar = getSupportActionBar();

        String[] materials = {"Plastic","Glass","Cardboard","Paper", "Metal" };
        String[] points = {
                "10 pts",
                "6 pts",
                "5 pts",
                "3 pts",
                "8 pts"
        };

        PointsAdapter adapter = new PointsAdapter(this, materials, points);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

    }
}