package com.greenkeycompany.exam.repository.model;

import com.greenkeycompany.exam.repository.realm.IdentityRealmObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tert0 on 06.10.2017.
 */

public class Rule extends RealmObject implements IdentityRealmObject {

    public static final String FIELD_CHAPTER_ID = "chapter.id";

    @PrimaryKey
    private int id;
    private String title;
    private Chapter chapter;
    private boolean descriptionCompleted;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDescriptionCompleted() {
        return descriptionCompleted;
    }

    public void setDescriptionCompleted(boolean descriptionCompleted) {
        this.descriptionCompleted = descriptionCompleted;
    }
}
