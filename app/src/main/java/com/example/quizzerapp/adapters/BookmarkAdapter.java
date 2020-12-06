package com.example.quizzerapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quizzerapp.R;
import com.example.quizzerapp.models.QuestionModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {

    private List<QuestionModel> questionModels;

    public BookmarkAdapter(List<QuestionModel> questionModels) {
        this.questionModels = questionModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(questionModels.get(position).getImageUrl(),
                questionModels.get(position).getCategory(),
                questionModels.get(position).getQuestion(),
                questionModels.get(position).getCorrectAns(),
                position);
    }

    @Override
    public int getItemCount() {
        return questionModels.size();
    }

    //inner class

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView question;
        private TextView answre;
        private TextView category;
        private CircleImageView circleImageView;
        private ImageButton buttonDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.question);
            answre = itemView.findViewById(R.id.answer);
            circleImageView = itemView.findViewById(R.id.circle_image_view_bookmark);
            category = itemView.findViewById(R.id.category_bookmark);
            buttonDelete = itemView.findViewById(R.id.btn_bookmark_delete);
        }

        private void setData(String imageUrl,String category,String question,String answer,int position){
            Glide.with(itemView.getContext()).load(imageUrl).into(circleImageView);
            this.question.setText( question );
            this.category.setText(category);
            this.answre.setText(answer);

            buttonDelete.setOnClickListener(v -> {
                questionModels.remove(position);
                notifyItemRemoved(position);
            });
        }
    }

}
