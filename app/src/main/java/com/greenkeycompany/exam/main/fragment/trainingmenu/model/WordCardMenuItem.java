package com.greenkeycompany.exam.main.fragment.trainingmenu.model;

/**
 * Created by tert0 on 06.10.2017.
 */

public class WordCardMenuItem {

    private int wordCardCount;
    private int wordCardCompletedCount;
    private boolean completed;

    public void setWordCardCount(int wordCardCount) {
        this.wordCardCount = wordCardCount;
    }

    public void setWordCardCompletedCount(int wordCardCompletedCount) {
        this.wordCardCompletedCount = wordCardCompletedCount;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
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
