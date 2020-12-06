package com.example.quizzerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.quizzerapp.adapters.BookmarkAdapter;
import com.example.quizzerapp.helpers.Utility;
import com.example.quizzerapp.models.QuestionModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BookmarkActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<QuestionModel> bookmarkList;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        Toolbar toolbar = findViewById(R.id.toolbar_bookmar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bookmarks");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView_bookmarks);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        sharedPreferences = getSharedPreferences(Utility.FILE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();

        getBookmarks();

        BookmarkAdapter bookmarkAdapter = new BookmarkAdapter(bookmarkList);
        recyclerView.setAdapter(bookmarkAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        storeBookmarks();
    }

    private void getBookmarks(){
        String json = sharedPreferences.getString(Utility.KEY_NAME,null);
        Type type = new TypeToken<List<QuestionModel>>(){}.getType();
        bookmarkList = gson.fromJson(json,type);
        if(bookmarkList == null){
            bookmarkList = new ArrayList<>();
        }
    }


    private void storeBookmarks(){
        String json = gson.toJson(bookmarkList);
        editor.putString(Utility.KEY_NAME,json);
        editor.commit();
    }
}