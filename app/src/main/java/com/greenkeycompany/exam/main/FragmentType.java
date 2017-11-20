package com.greenkeycompany.exam.main;

import android.support.annotation.Nullable;

/**
 * Created by tert0 on 04.10.2017.
 */

public enum FragmentType {
    MAIN(null),
    CHAPTER(MAIN),
    RULE_DETAIL(CHAPTER),
    RULE_DESCRIPTION(RULE_DETAIL),
    TRAINING_MENU(RULE_DETAIL),

    FINAL_EXAM(MAIN),
    FINAL_EXAM_RESULT(MAIN),

    CHAPTER_EXAM(CHAPTER),
    CHAPTER_EXAM_RESULT(CHAPTER),

    RULE_EXAM(RULE_DETAIL),
    RULE_EXAM_RESULT(RULE_DETAIL),

    WORD_CARD_SET_TRAINING(TRAINING_MENU),
    WORD_CARD_SET_TRAINING_RESULT(TRAINING_MENU);

    private FragmentType parent;
    FragmentType(FragmentType parent) {
        this.parent = parent;
    }

    @Nullable
    public FragmentType getParent() {
        return parent;
    }
}
