package com.greenkeycompany.exam.main.repository.model;

import com.greenkeycompany.exam.main.repository.realm.IdentityRealmObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tert0 on 06.10.2017.
 */

public class Chapter extends RealmObject implements IdentityRealmObject {

    @PrimaryKey
    private int id;
    private String title;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
