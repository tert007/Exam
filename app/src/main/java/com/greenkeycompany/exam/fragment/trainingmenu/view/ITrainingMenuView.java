package com.greenkeycompany.exam.fragment.trainingmenu.view;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import com.greenkeycompany.exam.fragment.trainingmenu.model.WordCardMenuItem;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

/**
 * Created by tert0 on 06.10.2017.
 */

public interface ITrainingMenuView extends MvpView {
    void setBackgroundColor(@ColorInt int color);
    void setTrainingModelItems(@NonNull List<WordCardMenuItem> wordCardMenuItemList);

    void requestToSetWordCardRulePointTrainingFragment(int rulePointId);
}
