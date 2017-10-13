package com.greenkeycompany.exam.fragment.rule.view;

import android.support.annotation.ColorInt;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by tert0 on 20.09.2017.
 */

public interface IRuleView extends MvpView {
    void setBackgroundColor(@ColorInt int color);
}
