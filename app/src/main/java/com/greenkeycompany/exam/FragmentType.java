package com.greenkeycompany.exam;

import android.support.annotation.Nullable;

/**
 * Created by tert0 on 04.10.2017.
 */

public enum FragmentType {
    MAIN_MENU(null),
    CHAPTER_MENU(MAIN_MENU),
    RULE_MENU(CHAPTER_MENU),
    RULE_DESCRIPTION(RULE_MENU),
    TRAINING_MENU(RULE_MENU),
    WORD_CARD_TRAINING(MAIN_MENU),
    WORD_CARD_RULE_TRAINING(RULE_MENU),
    WORD_CARD_RULE_POINT_TRAINING(TRAINING_MENU);

    private FragmentType parent;
    FragmentType(FragmentType parent) {
        this.parent = parent;
    }

    @Nullable
    public FragmentType getParent() {
        return parent;
    }
}
