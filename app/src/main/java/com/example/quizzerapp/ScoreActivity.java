package com.example.quizzerapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {

    private TextView score,total;
    private Button finish;
    private RatingBar ratingBar;
    private MediaPlayer mediaPlayerBad, mediaPlayerNormal , mediaPlayerBest;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        score = findViewById(R.id.textView_score);
        finish = findViewById(R.id.button_finish);
        ratingBar = findViewById(R.id.ratingBar);


        int localScore = getIntent().getIntExtra("score",0);
        int localTotal = getIntent().getIntExtra("total",0);

        float normalizedScore = ((float)(float)localScore/(float)localTotal)*5.0f;

        mediaPlayerBest = MediaPlayer.create(this,R.raw.sei_veramente_straordinario); // Max
        mediaPlayerNormal = MediaPlayer.create(this,R.raw.oh_fratm); // normal
        mediaPlayerBad = MediaPlayer.create(this,R.raw.bucchina_ma_fuss_strunz); // bad

        int min = ratingBar.getMin();
        int max = ratingBar.getMax();
        int mid = ((max-min)/2);

        if(normalizedScore >= min && normalizedScore <= (min + 1)){
            mediaPlayerBad.start();
        }else if(normalizedScore > (min + 1) && normalizedScore <= (mid - 1 ) ){
            mediaPlayerNormal.start();
        }else  if(normalizedScore > (mid - 1) && normalizedScore <= max){
            mediaPlayerBest.start();
        }



        ratingBar.setNumStars(5);
        ratingBar.setMin(0);
        ratingBar.setMax(5);
        ratingBar.setStepSize(0.5f);
        ratingBar.setRating(normalizedScore);
        YoYo.with(Techniques.FlipInX).duration(500).playOn(ratingBar);



        score.setText(localScore+"/"+localTotal);

        finish.setOnClickListener(v -> finish());

    }
}