package com.example.quizzerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    boolean isFirstTime = false;
    TextView textViewBchel;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        textViewBchel = findViewById(R.id.textViewBCHELE);
        textViewBchel.setTextColor(Color.BLACK);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isFirstTime = true;
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }, 2000);
    }

    @Override
    protected void onResume() {
        if(isFirstTime) finish();
        super.onResume();

    }

}