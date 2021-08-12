package com.example.quizzerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.quizzerapp.adapters.RecAdapter;
import com.example.quizzerapp.models.RecModel;

import java.util.ArrayList;
import java.util.List;

public class RecActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private RecyclerView recyclerView;
    private List<RecModel> recModels;

    private Dialog loadingDialog;


    public Context getContext() {return this;}

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec);



        Toolbar toolbar = findViewById(R.id.toolbar_rec);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Recs");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading);
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);

        recyclerView = findViewById(R.id.recyclerViewRec);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recModels = new ArrayList<>();
        RecAdapter recAdapter = new RecAdapter(recModels);
        recyclerView.setAdapter(recAdapter);

        loadingDialog.show();

        //get all elements from raw folder
        recModels.add(new RecModel(this,"Intant...",R.raw.intant));
        recModels.add(new RecModel(this,"Oh ma si strunz",R.raw.oh_ma_si_strunz));
        recModels.add(new RecModel(this,"oh stiamo noi giù",R.raw.oh_stiamo_noi_giu));
        recModels.add(new RecModel(this,"ohh scendi arrabbiato!",R.raw.ohhh_scendi_arrabiato));
        recModels.add(new RecModel(this,"ohh scendi",R.raw.ohh_scendi));
        recModels.add(new RecModel(this,"t sfong!",R.raw.tsfong));
        recModels.add(new RecModel(this,"Arriva...",R.raw.arriva));
        recModels.add(new RecModel(this,"Bucchinà ma fuss strunz ?!",R.raw.bucchina_ma_fuss_strunz));
        recModels.add(new RecModel(this,"Sei veramente straordinario!",R.raw.sei_veramente_straordinario));
        recModels.add(new RecModel(this,"Urlo pazzo!",R.raw.urlo_pazzo));
        recModels.add(new RecModel(this,"Urlo più pazzo!",R.raw.urlo_pazzo_2));
        recModels.add(new RecModel(this,"Bha...",R.raw.bha));
        recModels.add(new RecModel(this,"Omm e spaccimm",R.raw.omm_e_spaccimm));
        recModels.add(new RecModel(this,"Quant !?",R.raw.quant));
        recModels.add(new RecModel(this,"Uau...",R.raw.uau));
        recModels.add(new RecModel(this,"Uccidilo!",R.raw.uccidilo));
        recModels.add(new RecModel(this,"Oh fratm",R.raw.oh_fratm));

        recAdapter.notifyDataSetChanged();
        loadingDialog.dismiss();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        startActivity(new Intent(this,MainActivity.class));
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RecActivity.this,MainActivity.class));
    }
}