package com.greenkeycompany.exam.repository.model.result;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import com.greenkeycompany.exam.repository.model.Chapter;
import com.greenkeycompany.exam.repository.realm.IdentityRealmObject;
import com.greenkeycompany.exam.repository.realm.ResultRealmObject;
import com.greenkeycompany.exam.repository.realm.UnixTimeRealmObject;

/**
 * Created by tert0 on 20.10.2017.
 */

public class ChapterExamResult extends RealmObject implements IdentityRealmObject,
        ResultRealmObject, UnixTimeRealmObject {

    public static final String FIELD_CHAPTER_ID = "chapterId";

    @PrimaryKey
    private int id;

    private int chapterId;

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

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
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
