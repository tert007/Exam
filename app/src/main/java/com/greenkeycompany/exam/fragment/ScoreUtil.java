package com.greenkeycompany.exam.fragment;

import android.graphics.Color;
import android.support.annotation.ColorInt;

import java.util.Locale;

/**
 * Created by tert0 on 09.10.2017.
 */

public class ScoreUtil {

    public static final float MIN_SCORE = 0.0f;
    public static final float MEDIUM_SCORE = 3.0f;
    public static final float COMPLETED_SCORE = 4.5f;
    public static final float MAX_SCORE = 5.0f;

    @ColorInt
    public static int getReferenceColor(float score) {
        if (score == MIN_SCORE) return Color.GRAY;
        if (score < MEDIUM_SCORE) return Color.parseColor("#a2a6a5");
        if (score < COMPLETED_SCORE) return Color.parseColor("#FFFFCA28");
        if (score < MAX_SCORE) return Color.parseColor("#32BA7C");
        if (score == MAX_SCORE) return Color.parseColor("#0AA06E");

        return Color.BLACK;
    }

    public static float getScore(int trueAnswerCount, int count) {
        float scorePerAnswer = MAX_SCORE /  count;
        return trueAnswerCount * scorePerAnswer;
    }

    public static String convertScoreToString(float score) {
        return String.format(Locale.getDefault(), format, score);
    }

    private static final String format = "%1$.1f";

    public static String getScoreByString(int trueAnswerCount, int count) {
        return convertScoreToString(getScore(trueAnswerCount, count));
    }
}
