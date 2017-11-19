package com.greenkeycompany.exam.main.repository.model.status;

import com.greenkeycompany.exam.main.repository.realm.IdentityRealmObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tert0 on 11.11.2017.
 */

public class WordCardSetStatus extends RealmObject implements IdentityRealmObject {

    public static final String FIELD_WORD_CARD_SET_ID = "wordCardSetId";

    @PrimaryKey
    private int id;
    private int wordCardSetId;
    private boolean completed;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getWordCardSetId() {
        return wordCardSetId;
    }

    public void setWordCardSetId(int wordCardSetId) {
        this.wordCardSetId = wordCardSetId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
