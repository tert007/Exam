package com.greenkeycompany.exam.main.repository.model.result;

import com.greenkeycompany.exam.main.repository.realm.IdentityRealmObject;
import com.greenkeycompany.exam.main.repository.realm.UnixTimeRealmObject;
import com.greenkeycompany.exam.main.repository.realm.ResultRealmObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tert0 on 12.10.2017.
 */

public class FinalExamResult extends RealmObject implements IdentityRealmObject,
        UnixTimeRealmObject, ResultRealmObject {

    @PrimaryKey
    private int id;

    private float score;
    private long unixTime;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public float getScore() {
        return score;
    }

    @Override
    public void setScore(float score) {
        this.score = score;
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
