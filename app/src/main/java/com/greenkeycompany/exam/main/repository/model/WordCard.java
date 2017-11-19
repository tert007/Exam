package com.greenkeycompany.exam.main.repository.model;

import com.greenkeycompany.exam.main.repository.realm.IdentityRealmObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tert0 on 22.09.2017.
 */

public class WordCard extends RealmObject implements IdentityRealmObject {

    public static final String FILED_CHAPTER_ID = "chapterId";
    public static final String FILED_RULE_ID = "ruleId";
    public static final String FILED_RULE_POINT_ID = "rulePointId";
    public static final String FILED_WORD_CARD_SET_ID = "wordCardSetId";

    private int chapterId;
    private int ruleId;
    private int rulePointId;
    private int wordCardSetId;

    @PrimaryKey
    private int id;
    private String correctWord;
    private String incorrectWord;

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    public int getRulePointId() {
        return rulePointId;
    }

    public void setRulePointId(int rulePointId) {
        this.rulePointId = rulePointId;
    }

    public int getWordCardSetId() {
        return wordCardSetId;
    }

    public void setWordCardSetId(int wordCardSetId) {
        this.wordCardSetId = wordCardSetId;
    }

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
}
