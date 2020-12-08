package com.example.quizzerapp;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.quizzerapp.helpers.Utility;
import com.example.quizzerapp.models.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {



    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference rootRef = database.getReference(Utility.ROOT_PATH);
    private DatabaseReference setsRef = rootRef.child(Utility.SETS_PATH);

    private TextView question,noIndicator,textCategory;
    private FloatingActionButton bookmarkBtn;
    private LinearLayout optionsContainer;
    private Button shareBtn,nextBtn;
    private int count =0;
    private List<QuestionModel> list;
    private int position = 0;
    private int score =0;
    private int questionNum;
    private String category,imageUrl;
    private Dialog loadingDialog;

    private List<QuestionModel> bookmarkList;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    private int matchedQuestionPosition;

    ValueEventListener setsRefValueEventListener = null;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Toolbar toolbar = findViewById(R.id.toolbar_question);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences(Utility.FILE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();

        getBookmarks();


        category = getIntent().getStringExtra("category");
        questionNum = getIntent().getIntExtra("sets",1);
        imageUrl = getIntent().getStringExtra("imageUrl");

        question = findViewById(R.id.textView_question);
        noIndicator = findViewById(R.id.textView_number_indicator);
        bookmarkBtn = findViewById(R.id.btn_bookmark);
        optionsContainer = findViewById(R.id.linearLayout_options);
        shareBtn = findViewById(R.id.button_share);
        nextBtn = findViewById(R.id.button_next);
        textCategory = findViewById(R.id.textView_category);

        textCategory.setText(category+" "+questionNum);

        bookmarkBtn.setOnClickListener(v -> {
            YoYo.with(Techniques.Bounce).playOn(bookmarkBtn);
            if(modelMatch()){
                bookmarkList.remove(matchedQuestionPosition);
                bookmarkBtn.setImageDrawable(getDrawable(R.drawable.bookmark_border));
            }else {
                bookmarkList.add(list.get(position));
                bookmarkBtn.setImageDrawable(getDrawable(R.drawable.bookmark_border_selected));
            }
        });

        list = new ArrayList<>();


        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading);
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);



        loadingDialog.show();

        setsRefValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    QuestionModel q = snap.getValue(QuestionModel.class);
                    q.setCategory(category);
                    q.setImageUrl(imageUrl);
                    list.add(q);
                }
                if(list.size()>0){

                    for(int i=0;i<4;i++){
                        optionsContainer.getChildAt(i).setOnClickListener(v -> {
                            checkAnsw((Button)v);           //qui crasha perchè l'oggetto è ancora null ed io clicco sopra
                                                            //per correggerlo metterlo quando finisce l'animazione
                        });
                    }
                    playAnim(question,0,list.get(position).getQuestion());
                    nextBtn.setOnClickListener(v -> {
                        nextBtn.setEnabled(false);
                        nextBtn.setAlpha(0.5f);
                        enableOption(true);
                        position++;
                        if(position == list.size()){
                            Intent intent = new Intent(QuestionActivity.this,ScoreActivity.class);
                            intent.putExtra("score",score);
                            intent.putExtra("total",list.size());
                            startActivity(intent);
                            finish();
                            return;
                        }
                        count = 0;
                        playAnim(question,0,list.get(position).getQuestion());
                    });

                    shareBtn.setOnClickListener(v -> {
                        String body = list.get(position).getQuestion()+ "\n" +
                                list.get(position).getOptionA()+ "\n" +
                                list.get(position).getOptionB()+ "\n" +
                                list.get(position).getOptionC()+ "\n" +
                                list.get(position).getOptionD();
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("plain/text");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Quizzer challenge");
                        shareIntent.putExtra(Intent.EXTRA_TEXT,body);
                        startActivity(Intent.createChooser(shareIntent,"Share via"));
                    });

                }else{
                    finish();
                    Toast.makeText(QuestionActivity.this,"no questions",Toast.LENGTH_SHORT).show();
                }
                loadingDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(QuestionActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
            }

        };

        setsRef.child(category)
                .child("questions")
                .orderByChild("questionNum")
                .equalTo(questionNum)
                .addListenerForSingleValueEvent(setsRefValueEventListener);

                //nel leak abbiamo addValueEvent listner :
                //addValueEventListener() keep listening to query or database reference it is attached to.
                //But addListenerForSingleValueEvent() executes onDataChange method immediately and after executing that
                //method once, it stops listening to the reference location it is attached to.

    }


    private void playAnim(View view, int value, String data){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(350).setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
                if(modelMatch()){
                    bookmarkBtn.setImageDrawable(getDrawable(R.drawable.bookmark_border_selected));
                }else {
                    bookmarkBtn.setImageDrawable(getDrawable(R.drawable.bookmark_border));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(setsRefValueEventListener!=null)
        setsRef.removeEventListener(setsRefValueEventListener);
    }

    private void getBookmarks(){
        String json = sharedPreferences.getString(Utility.KEY_NAME,null);
        Type type = new TypeToken<List<QuestionModel>>(){}.getType();
        bookmarkList = gson.fromJson(json,type);
        if(bookmarkList == null){
            bookmarkList = new ArrayList<>();
        }
    }

    private boolean modelMatch(){
        boolean matched = false;
        int pos=0;
        for (QuestionModel questionModel : bookmarkList) {
            if(questionModel.getQuestion().equals(list.get(position).getQuestion())
               && questionModel.getCorrectAns().equals(list.get(position).getCorrectAns())
               && questionModel.getQuestionNum() == list.get(position).getQuestionNum()){
                    matched = true;
                    matchedQuestionPosition = pos;
            }
            pos++;
        }
        return matched;
    }

    private void storeBookmarks(){
        String json = gson.toJson(bookmarkList);
        editor.putString(Utility.KEY_NAME,json);
        editor.commit();
    }
}