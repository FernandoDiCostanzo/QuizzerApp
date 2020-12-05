package com.example.quizzerapp.models;

public class CategoryModel {
    private String imageUrl,title;
    private int sets;

    public CategoryModel() {
        //dafault constructor for firebase
    }

    public CategoryModel(String imageUrl, String title, int sets) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.sets = sets;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }
}
