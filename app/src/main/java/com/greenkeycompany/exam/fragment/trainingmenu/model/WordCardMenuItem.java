package com.greenkeycompany.exam.fragment.trainingmenu.model;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.repository.model.RulePoint;

/**
 * Created by tert0 on 06.10.2017.
 */

public class WordCardMenuItem {

    private long wordCardCount;
    private RulePoint rulePoint;

    public WordCardMenuItem(@NonNull RulePoint rulePoint, long wordCardCount) {
        this.rulePoint = rulePoint;
        this.wordCardCount = wordCardCount;
    }

    public RulePoint getRulePoint() {
        return rulePoint;
    }

    public long getWordCardCount() {
        return wordCardCount;
    }
}
