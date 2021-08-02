package com.example.quizzerapp.models;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class QuestionModel {
    private String imageUrl,category,question,optionA,optionB, optionC, optionD, correctAns;
    private int questionNum;

    public QuestionModel() {
        //default for firebase
    }

    public QuestionModel(String imageUrl, String category, String question, String optionA, String optionB, String optionC, String optionD, String currectAnsw, int questionNum) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAns = currectAnsw;
        this.questionNum = questionNum;
        this.imageUrl = imageUrl;
        this.category = category;
    }


    public int getQuestionNum() {
        return questionNum;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(String correctAns) {
        this.correctAns = correctAns;
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return "Category :" + category + "\n"
                + "Num :" + questionNum + "\n"
                + "RispA :" + optionA + "\n"
                + "RispB :" + optionB + "\n"
                + "RispC :" + optionC + "\n"
                + "RispD :" + optionD + "\n"
                + "CorrectAns :" + correctAns + "\n";
    }
}
