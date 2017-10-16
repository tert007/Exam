package com.greenkeycompany.exam.fragment.chapter.model;

/**
 * Created by tert0 on 15.10.2017.
 */

public class RuleMenuItem {
    private final String title;
    private final float score;
    private final int wordCardCount;
    private final int wordCardCompletedCount;

    public RuleMenuItem(String title, float score, int wordCardCount, int wordCardCompletedCount) {
        this.title = title;
        this.score = score;
        this.wordCardCount = wordCardCount;
        this.wordCardCompletedCount = wordCardCompletedCount;
    }

    public String getTitle() {
        return title;
    }

    public float getScore() {
        return score;
    }

    public int getWordCardCount() {
        return wordCardCount;
    }

    public int getWordCardCompletedCount() {
        return wordCardCompletedCount;
    }
}
