package com.example.quizzerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.util.DebugUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quizzerapp.helpers.Utility;
import com.example.quizzerapp.models.QuestionModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class CreateQuestionActivity extends AppCompatActivity {

    private Spinner spinnerCategory;
    private Spinner spinnerDifficultyLevels;
    private Button commitBtn;
    private EditText editTextQuestion;
    private EditText editTextRespA;
    private EditText editTextRespB;
    private EditText editTextRespC;
    private EditText editTextRespD;
    private RadioGroup radioGroup;
    private Dialog loadingDialog;
    private Dialog successDialog;

    private String selectedCategory;
    private String question;
    private String responseA;
    private String responseB;
    private String responseC;
    private String responseD;
    private String curretAnsw;
    private String selectedDifficultyLevel;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = database.getReference(Utility.ROOT_PATH);
    DatabaseReference setsRef = rootRef.child(Utility.SETS_PATH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);

        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerDifficultyLevels = findViewById(R.id.spinnerDifficulty);
        commitBtn = findViewById(R.id.sendQuestionBtn);
        editTextQuestion = findViewById(R.id.textView_question);
        editTextRespA = findViewById(R.id.textView_rispA);
        editTextRespB = findViewById(R.id.textView_rispB);
        editTextRespC = findViewById(R.id.textView_rispC);
        editTextRespD = findViewById(R.id.textView_rispD);
        radioGroup = findViewById(R.id.radioGroupCorrectAnsw);

        Toolbar toolbar = findViewById(R.id.toolbar_question);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Crea la tua domanda!");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading);
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);

        successDialog = new Dialog(this);
        successDialog.setContentView(R.layout.success_push);
        successDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        successDialog.setCancelable(true);

        ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(this,R.array.category,R.layout.support_simple_spinner_dropdown_item);
        adapterCategory.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterCategory);

        ArrayAdapter<CharSequence> adapterDifficulty = ArrayAdapter.createFromResource(this,R.array.difficultyLevel,R.layout.support_simple_spinner_dropdown_item);
        adapterDifficulty.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerDifficultyLevels.setAdapter(adapterDifficulty);

        commitBtn.setOnClickListener(v -> {
            loadingDialog.show();
            if(checkUI()) {
                selectedCategory = spinnerCategory.getSelectedItem().toString();
                selectedDifficultyLevel = spinnerDifficultyLevels.getSelectedItem().toString();
                question = editTextQuestion.getText().toString();
                responseA = editTextRespA.getText().toString();
                responseB = editTextRespB.getText().toString();
                responseC = editTextRespC.getText().toString();
                responseD = editTextRespD.getText().toString();
                curretAnsw = getCorrectAnsw();
                QuestionModel questionModel = new QuestionModel(null,selectedCategory ,question
                ,responseA,responseB,responseC,responseD,curretAnsw,Integer.parseInt(selectedDifficultyLevel));

                String currentDate = Calendar.getInstance().getTime().toString();
                try {
                    setsRef.child(selectedCategory).child("questions").child("question"+ currentDate).setValue(questionModel);
                    successDialog.show();
                    Toast t = Toast.makeText(this,"Domanda inserita con successo !",Toast.LENGTH_LONG);
                    t.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onBackPressed();
                        }
                    }, t.getDuration());
                }catch (Exception e){
                    Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
                }finally {
                    loadingDialog.dismiss();
                    successDialog.dismiss();
                }


                Log.d("CANNIN", questionModel.toString());
            }
            loadingDialog.dismiss();
            if(successDialog.isShowing()) successDialog.dismiss();
        });



    }

    private boolean checkEditText(EditText editText){
        if(editText.getText().toString().isEmpty()) {
            editText.setError("Il campo non può essere vuoto!");
            return false;
        }else
            return true;

    }

    private boolean checkRadioGroup(RadioGroup radioGroup){
        if(radioGroup.getCheckedRadioButtonId() != -1){
            return true;
        }else{
            Toast.makeText(this,"Seleziona la risposta corretta !",Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private boolean checkUI(){
        return checkEditText(editTextQuestion) ||
                checkEditText(editTextRespA) ||
                checkEditText(editTextRespB) ||
                checkEditText(editTextRespC) ||
                checkEditText(editTextRespD) ||
                checkRadioGroup(radioGroup);
    }

    private String getCorrectAnsw(){
        if(checkRadioGroup(radioGroup)){
            int id = radioGroup.getCheckedRadioButtonId();
            switch (id){
                case 1 :
                    return responseA;
                case 2 :
                    return responseB;
                case 3 :
                    return responseC;
                case 4:
                    return responseD;
            }
        }
        return "";
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(CreateQuestionActivity.this,MainActivity.class));
    }
}