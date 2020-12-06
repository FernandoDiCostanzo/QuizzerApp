package com.example.quizzerapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.quizzerapp.adapters.GridAdapter;

public class SetsActivity extends AppCompatActivity {
private GridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        Toolbar toolbar = findViewById(R.id.toolbar_sets);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String category = getIntent().getStringExtra("category");
        int sets = getIntent().getIntExtra("sets",0);
        String imageUrl = getIntent().getStringExtra("imageUrl");

        getSupportActionBar().setTitle(category);

        gridView = findViewById(R.id.gridView);

        GridAdapter gridAdapter = new GridAdapter(sets,category,imageUrl);
        gridView.setAdapter(gridAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
