package com.greenkeycompany.exam.repository.model;

import com.greenkeycompany.exam.repository.realm.IdentityRealmObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tert0 on 22.09.2017.
 */

public class WordCard extends RealmObject implements IdentityRealmObject {

    public static final String FILED_CHAPTER_ID = "rulePoint.rule.chapter.id";
    public static final String FILED_RULE_POINT_ID = "rulePoint.id";
    public static final String FILED_RULE_ID = "rulePoint.rule.id";

    @PrimaryKey
    private int id;
    private String correctWord;
    private String incorrectWord;

    private RulePoint rulePoint;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getCorrectWord() {
        return correctWord;
    }

    public void setCorrectWord(String correctWord) {
        this.correctWord = correctWord;
    }

    public String getIncorrectWord() {
        return incorrectWord;
    }

    public void setIncorrectWord(String incorrectWord) {
        this.incorrectWord = incorrectWord;
    }

    public RulePoint getRulePoint() {
        return rulePoint;
    }

    public void setRulePoint(RulePoint rulePoint) {
        this.rulePoint = rulePoint;
    }
}
