package com.greenkeycompany.exam.main;

import android.support.annotation.Nullable;

/**
 * Created by tert0 on 04.10.2017.
 */

public enum FragmentType {
    MAIN(null),
    CHAPTER(MAIN),
    RULE(CHAPTER),
    RULE_DESCRIPTION(RULE),
    TRAINING_MENU(RULE),

    FINAL_EXAM(MAIN),
    FINAL_EXAM_RESULT(MAIN),

    CHAPTER_EXAM(CHAPTER),
    CHAPTER_EXAM_RESULT(CHAPTER),

    RULE_EXAM(RULE),
    RULE_EXAM_RESULT(RULE),

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
