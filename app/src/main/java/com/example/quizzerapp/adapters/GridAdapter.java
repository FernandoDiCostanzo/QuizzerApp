package com.example.quizzerapp.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quizzerapp.R;
import com.example.quizzerapp.QuestionActivity;

public class GridAdapter extends BaseAdapter {

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    private int sets = 0 ;
    private String category,imageUrl;

    public GridAdapter(int sets,String category,String imageUrl) {
        this.category = category;
        this.sets = sets;
        this.imageUrl = imageUrl;
    }

    public GridAdapter() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int getCount() {
        return sets;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_item,parent,false);
        }else {
            view = convertView;
        }

        view.setOnClickListener(v -> {
            Intent questionIntent = new Intent(parent.getContext(), QuestionActivity.class);
            questionIntent.putExtra("category",category);
            questionIntent.putExtra("sets",position+1);
            questionIntent.putExtra("imageUrl",imageUrl);
            parent.getContext().startActivity(questionIntent);
        });

        ((TextView)view.findViewById(R.id.textView)).setText(String.valueOf(position+1));

        return view;
    }
}
