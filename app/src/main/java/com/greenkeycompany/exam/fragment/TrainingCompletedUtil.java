package com.greenkeycompany.exam.fragment;

/**
 * Created by tert0 on 15.10.2017.
 */

public class TrainingCompletedUtil {

    private static final float COMPLETED_LIMIT_PERCENT = 70;
    private static final float MAX_PERCENT = 100;

    public static boolean isCompleted(int trueAnswerCount, int wordCardCount) {
        if (trueAnswerCount * MAX_PERCENT >= COMPLETED_LIMIT_PERCENT * wordCardCount)
            return true;

        return false;
    }
}
