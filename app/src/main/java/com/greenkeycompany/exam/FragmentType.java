package com.greenkeycompany.exam;

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

    WORD_CARD_CHAPTER_TRAINING(CHAPTER),
    WORD_CARD_CHAPTER_RESULT(CHAPTER),

    WORD_CARD_RULE_TRAINING(RULE),
    WORD_CARD_RULE_RESULT(RULE),

    WORD_CARD_RULE_POINT_TRAINING(TRAINING_MENU),
    WORD_CARD_RULE_POINT_RESULT(TRAINING_MENU);

    private FragmentType parent;
    FragmentType(FragmentType parent) {
        this.parent = parent;
    }

    @Nullable
    public FragmentType getParent() {
        return parent;
    }
}
