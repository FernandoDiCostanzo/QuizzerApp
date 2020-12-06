package com.example.quizzerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.quizzerapp.adapters.BookmarkAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.function.BooleanSupplier;

public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private Button bookMarkButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        startButton = findViewById(R.id.button_startQuitz);
        bookMarkButton = findViewById(R.id.button_bookmark);


        this.startButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,CategoryActivities.class);
            startActivity(intent);
        });

        this.bookMarkButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BookmarkActivity.class);
            startActivity(intent);
        });
    }
}