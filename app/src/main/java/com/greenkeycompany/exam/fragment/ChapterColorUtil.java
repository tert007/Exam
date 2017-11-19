package com.greenkeycompany.exam.fragment;

import android.graphics.Color;
import android.support.annotation.ColorInt;

/**
 * Created by tert0 on 09.10.2017.
 */

public class ChapterColorUtil {

    @ColorInt
    public static int getColor(int chapterId) {
        switch (chapterId) {
            case 1 : return Color.parseColor("#489D9B");
            case 2 : return Color.parseColor("#96C884");
            case 3 : return Color.parseColor("#5BACB4");
            case 4 : return Color.parseColor("#e16f3e");
            default: return Color.BLACK;
        }
    }
}
