package com.greenkeycompany.exam.main.repository.model;

import com.greenkeycompany.exam.main.repository.realm.IdentityRealmObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tert0 on 10.11.2017.
 */

public class WordCardSet extends RealmObject implements IdentityRealmObject {

    public static final String FILED_RULE_ID = "ruleId";

    @PrimaryKey
    private int id;
    private int ruleId;

    private int size;

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
