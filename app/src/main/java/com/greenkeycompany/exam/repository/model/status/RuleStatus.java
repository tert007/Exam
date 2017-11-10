package com.greenkeycompany.exam.repository.model.status;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import com.greenkeycompany.exam.repository.realm.IdentityRealmObject;

/**
 * Created by tert0 on 11.11.2017.
 */

public class RuleStatus extends RealmObject implements IdentityRealmObject {

    public static final String FIELD_RULE_ID = "ruleId";

    @PrimaryKey
    private int id;
    private int ruleId;
    private boolean learned;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    public boolean isLearned() {
        return learned;
    }

    public void setLearned(boolean learned) {
        this.learned = learned;
    }
}
