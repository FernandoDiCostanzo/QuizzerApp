package com.example.quizzerapp.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quizzerapp.R;
import com.example.quizzerapp.SetsActivity;
import com.example.quizzerapp.models.CategoryModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryModel> categoryModelList;

    public CategoryAdapter(List<CategoryModel> categoryModelList){
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(categoryModelList.get(position).getImageUrl(),categoryModelList.get(position).getTitle(),categoryModelList.get(position).getSets());
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView circleImageView;
        private TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.circle_image_view);
            title = itemView.findViewById(R.id.title);
        }

        private void setData(String url,final String title,int sets){
            Glide.with(itemView.getContext()).load(url).into(circleImageView);
            this.title.setText(title);

            itemView.setOnClickListener(v -> {
                Intent setIntent = new Intent(itemView.getContext(), SetsActivity.class);
                setIntent.putExtra("category",title);
                setIntent.putExtra("sets",sets);
                itemView.getContext().startActivity(setIntent);
            });
        }

    }
}
