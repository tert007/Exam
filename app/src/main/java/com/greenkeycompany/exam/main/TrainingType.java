package com.greenkeycompany.exam.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by tert0 on 20.10.2017.
 */

public enum  TrainingType {
    FINAL_EXAM,
    CHAPTER_EXAM,
    RULE_EXAM,
    WORD_CARD_SET_TRAINING;

    public static FragmentType getReferenceTrainingFragmentType(@NonNull TrainingType trainingType) {
        switch (trainingType) {
            case WORD_CARD_SET_TRAINING:
                return FragmentType.WORD_CARD_SET_TRAINING;
            case RULE_EXAM:
                return  FragmentType.RULE_EXAM;
            case CHAPTER_EXAM:
                return FragmentType.CHAPTER_EXAM;
            case FINAL_EXAM:
                return FragmentType.FINAL_EXAM;
        }
        return null;
    }

    public static FragmentType getReferenceResultFragmentType(@NonNull TrainingType trainingType) {
        switch (trainingType) {
            case WORD_CARD_SET_TRAINING:
                return FragmentType.WORD_CARD_SET_TRAINING_RESULT;
            case RULE_EXAM:
                return  FragmentType.RULE_EXAM_RESULT;
            case CHAPTER_EXAM:
                return FragmentType.CHAPTER_EXAM_RESULT;
            case FINAL_EXAM:
                return FragmentType.FINAL_EXAM_RESULT;
        }
        return null;
    }
}
