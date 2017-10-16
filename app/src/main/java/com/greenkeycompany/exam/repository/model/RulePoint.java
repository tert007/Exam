package com.greenkeycompany.exam.repository.model;

import com.greenkeycompany.exam.repository.realm.IdentityRealmObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tert0 on 06.10.2017.
 */

public class RulePoint extends RealmObject implements IdentityRealmObject {

    public static final String FIELD_RULE_ID = "rule.id";
    public static final String FIELD_WORD_CARD_COMPLETED_COUNT = "wordCardCompletedCount";

    @PrimaryKey
    private int id;
    private String title;
    private String description;

    private boolean completed;
    private int wordCardCompletedCount;

    private Rule rule;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getWordCardCompletedCount() {
        return wordCardCompletedCount;
    }

    public void setWordCardCompletedCount(int wordCardCompletedCount) {
        this.wordCardCompletedCount = wordCardCompletedCount;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }
}
