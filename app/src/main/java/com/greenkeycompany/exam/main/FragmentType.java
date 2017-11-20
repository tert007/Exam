package com.greenkeycompany.exam.main;

import android.support.annotation.NonNull;
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
    FINAL_EXAM_RESULT(FINAL_EXAM),

    CHAPTER_EXAM(CHAPTER),
    CHAPTER_EXAM_RESULT(CHAPTER_EXAM),

    RULE_EXAM(RULE_DETAIL),
    RULE_EXAM_RESULT(RULE_EXAM),

    WORD_CARD_SET_TRAINING(TRAINING_MENU),
    WORD_CARD_SET_TRAINING_RESULT(WORD_CARD_SET_TRAINING);

    private FragmentType parent;
    FragmentType(FragmentType parent) {
        this.parent = parent;
    }

    @Nullable
    public FragmentType getParent() {
        return parent;
    }

    public static boolean isResultFragmentType(@NonNull FragmentType fragmentType) {
        if (fragmentType == FragmentType.WORD_CARD_SET_TRAINING_RESULT ||
                fragmentType == FragmentType.RULE_EXAM_RESULT ||
                fragmentType == FragmentType.CHAPTER_EXAM_RESULT ||
                fragmentType == FragmentType.FINAL_EXAM_RESULT) {
            return true;
        }

        return false;
    }

    public static boolean isTrainingFragmentType(@NonNull FragmentType fragmentType) {
        if (fragmentType == FragmentType.WORD_CARD_SET_TRAINING ||
                fragmentType == FragmentType.RULE_EXAM ||
                fragmentType == FragmentType.CHAPTER_EXAM ||
                fragmentType == FragmentType.FINAL_EXAM) {
            return true;
        }

        return false;
    }

}
