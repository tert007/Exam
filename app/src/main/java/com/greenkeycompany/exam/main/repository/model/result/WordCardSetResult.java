package com.greenkeycompany.exam.main.repository.model.result;

import com.greenkeycompany.exam.main.repository.realm.IdentityRealmObject;
import com.greenkeycompany.exam.main.repository.realm.UnixTimeRealmObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tert0 on 12.10.2017.
 */

public class WordCardSetResult extends RealmObject implements IdentityRealmObject,
        UnixTimeRealmObject {

    public static final String FIELD_WORD_CARD_COMPLETED_COUNT_ = "wordCardCompletedCount";
    public static final String FIELD_WORD_CARD_SET_ID = "wordCardSetId";

    @PrimaryKey
    private int id;

    private int wordCardSetId;

    private int wordCardCompletedCount;

    private long unixTime;

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

    public int getWordCardCompletedCount() {
        return wordCardCompletedCount;
    }

    public void setWordCardCompletedCount(int wordCardCompletedCount) {
        this.wordCardCompletedCount = wordCardCompletedCount;
    }

    @Override
    public long getUnixTime() {
        return unixTime;
    }

    @Override
    public void setUnixTime(long unixTime) {
        this.unixTime = unixTime;
    }
}
