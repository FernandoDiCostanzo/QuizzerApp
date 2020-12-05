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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.quizzerapp.helpers.Path;
import com.example.quizzerapp.models.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = database.getReference(Path.ROOT_PATH);
    DatabaseReference setsRef = rootRef.child(Path.SETS_PATH);
    private TextView question,noIndicator,textCategory;
    private FloatingActionButton bookmark;
    private LinearLayout optionsContainer;
    private Button shareBtn,nextBtn;
    private int count =0;
    private List<QuestionModel> list;
    private int position = 0;
    private int score =0;
    private int questionNum;
    private String category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Toolbar toolbar = findViewById(R.id.toolbar_question);
        setSupportActionBar(toolbar);

        category = getIntent().getStringExtra("category");
        questionNum = getIntent().getIntExtra("sets",1);

        question = findViewById(R.id.textView_question);
        noIndicator = findViewById(R.id.textView_number_indicator);
        bookmark = findViewById(R.id.btn_bookmark);
        optionsContainer = findViewById(R.id.linearLayout_options);
        shareBtn = findViewById(R.id.button_share);
        nextBtn = findViewById(R.id.button_next);
        textCategory = findViewById(R.id.textView_category);

        textCategory.setText(category+" "+questionNum);

        list = new ArrayList<>();




        setsRef.child(category)
                .child("questions")
                .orderByChild("questionNum")
                .equalTo(questionNum)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    QuestionModel q = snap.getValue(QuestionModel.class);
                    list.add(q);
                }
                if(list.size()>0){

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
                }else{
                    finish();
                    Toast.makeText(QuestionActivity.this,"no questions",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(QuestionActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
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
        if(selectedOption.getText().equals(list.get(position).getCorrectAns())){
            score++;
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF33")));
        }else{
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E62A2A")));
            Button correctOption = (Button)optionsContainer.findViewWithTag(list.get(position).getCorrectAns());
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