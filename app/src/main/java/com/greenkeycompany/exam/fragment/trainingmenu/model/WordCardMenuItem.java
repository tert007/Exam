package com.greenkeycompany.exam.fragment.trainingmenu.model;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.repository.model.RulePoint;

/**
 * Created by tert0 on 06.10.2017.
 */

public class WordCardMenuItem {

    private String title;
    private int wordCardCount;
    private int wordCardCompletedCount;
    private boolean completed;

    public WordCardMenuItem() {
    }

    public WordCardMenuItem(String title, int wordCardCount, int wordCardCompletedCount, boolean completed) {
        this.title = title;
        this.wordCardCount = wordCardCount;
        this.wordCardCompletedCount = wordCardCompletedCount;
        this.completed = completed;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWordCardCount(int wordCardCount) {
        this.wordCardCount = wordCardCount;
    }

    public void setWordCardCompletedCount(int wordCardCompletedCount) {
        this.wordCardCompletedCount = wordCardCompletedCount;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getTitle() {
        return title;
    }

    public int getWordCardCount() {
        return wordCardCount;
    }

    public int getWordCardCompletedCount() {
        return wordCardCompletedCount;
    }

    public boolean isCompleted() {
        return completed;
    }
}
