package com.greenkeycompany.exam.repository.model;

import com.greenkeycompany.exam.repository.realm.IdentityRealmObject;
import com.greenkeycompany.exam.repository.realm.ResultRealmObject;
import com.greenkeycompany.exam.repository.realm.UnixTimeRealmObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tert0 on 12.10.2017.
 */

public class RulePointTrainingResult extends RealmObject implements IdentityRealmObject,
        UnixTimeRealmObject, ResultRealmObject {

    public static final String FIELD_RULE_POINT_ID = "rulePoint.id";

    @PrimaryKey
    private int id;

    private RulePoint rulePoint;

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

    public RulePoint getRulePoint() {
        return rulePoint;
    }

    public void setRulePoint(RulePoint rulePoint) {
        this.rulePoint = rulePoint;
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
