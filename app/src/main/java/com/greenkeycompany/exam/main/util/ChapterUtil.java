package com.greenkeycompany.exam.main.util;

import android.graphics.Color;
import android.support.annotation.ColorInt;

/**
 * Created by tert0 on 09.10.2017.
 */

public class ChapterUtil {

    /**
     * Финальный экзамен представлен в списке как последняя глава с id = 0;
     */
    public static final int FINAL_EXAM_ID = 0;

    @ColorInt
    public static int getColor(int chapterId) {
        switch (chapterId) {
            case FINAL_EXAM_ID : return Color.parseColor("#323132");
            case 1 : return Color.parseColor("#489D9B");
            case 2 : return Color.parseColor("#96C884");
            case 3 : return Color.parseColor("#5BACB4");
            case 4 : return Color.parseColor("#e16f3e");
            default: return Color.BLACK;
        }
    }

    public static boolean isPremiumChapter(int chapterId) {
        switch (chapterId) {
            case 3:
            case 4:
                return true;

            default:
                return false;
        }
    }
}
