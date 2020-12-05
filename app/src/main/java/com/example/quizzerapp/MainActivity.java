package com.example.quizzerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        this.bookMarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}