package com.example.quizzerapp;

import android.animation.Animator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.quizzerapp.models.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    private TextView question,noIndicator;
    private FloatingActionButton bookmark;
    private LinearLayout optionsContainer;
    private Button shareBtn,nextBtn;
    private int count =0;
    private List<QuestionModel> list;
    private int position = 0;
    private int score =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //Toolbar toolbar = findViewById(R.id.toolbar_question);
        //setSupportActionBar(toolbar);

        question = findViewById(R.id.textView_question);
        noIndicator = findViewById(R.id.textView_number_indicator);
        bookmark = findViewById(R.id.btn_bookmark);
        optionsContainer = findViewById(R.id.linearLayout_options);
        shareBtn = findViewById(R.id.button_share);
        nextBtn = findViewById(R.id.button_next);

        list = new ArrayList<>();
        /*
        list.add(new QuestionModel("Question1","a","b","c","d","a"));
        list.add(new QuestionModel("Question2","a","b","c","d","b"));
        list.add(new QuestionModel("Question3","a","b","c","d","b"));
        list.add(new QuestionModel("Question4","a","b","c","d","d"));
        list.add(new QuestionModel("Question5","a","b","c","d","c"));
        list.add(new QuestionModel("Question6","a","b","c","d","a"));
        list.add(new QuestionModel("Question7","a","b","c","d","a"));
        list.add(new QuestionModel("Question8","a","b","c","d","b"));
        */
        for(int i=0;i<4;i++){
            optionsContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    checkAnsw(((Button)v));
                }
            });
        }



        playAnim(question,0,list.get(position).getQuestion());

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                nextBtn.setEnabled(false);
                nextBtn.setAlpha(0.5f);
                enableOption(true);
                position++;
                if(position == list.size()){
                    //score activity
                    return;
                }
                count = 0;
                playAnim(question,0,list.get(position).getQuestion());
            }
        });

    }

    private void playAnim(View view, int value,String data){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(350).setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if(value == 0 && count<4){
                    String option = new String();
                    if(count == 0){
                        option = list.get(position).getOptionA();
                    }else if(count == 1){
                        option = list.get(position).getOptionB();
                    }else if(count == 2){
                        option = list.get(position).getOptionC();
                    }else if(count == 3){
                        option = list.get(position).getOptionD();
                    }

                    playAnim(optionsContainer.getChildAt(count),0,option);
                    count++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(value == 0){
                    try {
                        ((TextView)view).setText(data);
                        noIndicator.setText((position+1)+"/"+list.size());
                    }catch (ClassCastException ex){
                        ((Button)view).setText(data);
                    }
                    view.setTag(data);
                    playAnim(view,1,data);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkAnsw(Button selectedOption){
        enableOption(false);
        nextBtn.setEnabled(true);
        nextBtn.setAlpha(1);
        if(selectedOption.getText().equals(list.get(position).getCurrectAnsw())){
            score++;
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF33")));
        }else{
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E62A2A")));
            Button correctOption = (Button)optionsContainer.findViewWithTag(list.get(position).getCurrectAnsw());
            correctOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF33")));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void enableOption(boolean en){
        for(int i=0;i<4;i++){
            optionsContainer.getChildAt(i).setEnabled(en);
            if(en){
                optionsContainer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#DADADA")));
            }
        }
    }
}