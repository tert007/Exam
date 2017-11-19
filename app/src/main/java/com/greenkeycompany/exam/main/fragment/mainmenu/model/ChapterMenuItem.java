package com.greenkeycompany.exam.main.fragment.mainmenu.model;

/**
 * Created by tert0 on 21.10.2017.
 */

public class ChapterMenuItem {
    private int id;
    private String title;
    private float score;

    public ChapterMenuItem(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public ChapterMenuItem(int id, String title, float score) {
        this.id = id;
        this.title = title;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public float getScore() {
        return score;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
