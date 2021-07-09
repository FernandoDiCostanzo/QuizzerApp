package com.example.quizzerapp.models;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.quizzerapp.RecActivity;

public class RecModel {

    private String title;
    private int resId;
    private MediaPlayer player;

    public MediaPlayer getPlayer() {
        return player;
    }

    public RecModel(Context context, String title, int resId) {
        this.title = title;
        this.resId = resId;
        player = MediaPlayer.create(context,resId);
    }

    public String getTitle() {
        return title;
    }

    public int getResId() {
        return resId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
