package com.greenkeycompany.exam.repository.model;

import com.greenkeycompany.exam.repository.realm.IdentityRealmObject;
import com.greenkeycompany.exam.repository.realm.ResultRealmObject;
import com.greenkeycompany.exam.repository.realm.UnixTimeRealmObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tert0 on 12.10.2017.
 */

public class RulePointResult extends RealmObject implements IdentityRealmObject,
        UnixTimeRealmObject {

    public static final String FIELD_WORD_CARD_COMPLETED_COUNT_ = "wordCardCompletedCount";
    public static final String FIELD_RULE_POINT_ID = "rulePoint.id";

    @PrimaryKey
    private int id;

    private RulePoint rulePoint;

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

    public RulePoint getRulePoint() {
        return rulePoint;
    }

    public void setRulePoint(RulePoint rulePoint) {
        this.rulePoint = rulePoint;
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
