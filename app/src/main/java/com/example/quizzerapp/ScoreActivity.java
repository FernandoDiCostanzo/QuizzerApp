package com.example.quizzerapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class ScoreActivity extends AppCompatActivity {

    private TextView score,total;
    private Button finish;
    private RatingBar ratingBar;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        int localScore = getIntent().getIntExtra("score",0);
        int localTotal = getIntent().getIntExtra("total",0);

        score = findViewById(R.id.textView_score);
        finish = findViewById(R.id.button_finish);
        ratingBar = findViewById(R.id.ratingBar);

        float normalizedScore = ((float)(float)localScore/(float)localTotal)*5.0f;
        ratingBar.setNumStars(5);
        ratingBar.setMin(0);
        ratingBar.setMax(5);
        ratingBar.setStepSize(0.5f);
        ratingBar.setRating(normalizedScore);
        YoYo.with(Techniques.FlipInX).duration(500).playOn(ratingBar);


        score.setText(localScore+"/"+localTotal);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}