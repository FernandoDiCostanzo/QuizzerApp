package com.example.quizzerapp.adapters;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.quizzerapp.CategoryActivities;
import com.example.quizzerapp.R;
import com.example.quizzerapp.RecActivity;
import com.example.quizzerapp.models.RecModel;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {

    private List<RecModel> recModelsList;

    public RecAdapter(List<RecModel> recModelsList) {
        this.recModelsList = recModelsList;
    }

    @NotNull
    @Override
    public RecAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_item,parent,false);
        return new RecAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.setData(position, recModelsList.get(position).getResId(),recModelsList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return recModelsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private Button playButton;
        private ImageView shareButton;
        MediaPlayer player;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            playButton = itemView.findViewById(R.id.button_playRec);
            shareButton = itemView.findViewById(R.id.imageView_recShare);
        }

        public void setData(int position, int resId,  String title){
            playButton.setText(title);
            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    player = recModelsList.get(position).getPlayer();
                    int duration = player.getDuration();
                    YoYo.with(Techniques.Pulse).duration(duration).playOn(playButton);
                    player.start();
                }
            });
        }


    }


}
