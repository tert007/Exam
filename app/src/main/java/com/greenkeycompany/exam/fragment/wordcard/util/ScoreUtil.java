package com.greenkeycompany.exam.fragment.wordcard.util;

import java.util.Locale;

/**
 * Created by tert0 on 23.09.2017.
 */

public class ScoreUtil {

    private static final float MAX_SCORE = 5.0f;

    private static final String format = "%1$.2f";

    public static String getScore(int wordCardCount, int trueAnswerCount) {
        float scorePerAnswer = MAX_SCORE /  wordCardCount;
        float score = trueAnswerCount * scorePerAnswer;

        return String.format(Locale.getDefault(), format, score);
    }
}
