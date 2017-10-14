package com.greenkeycompany.exam.activity.view;

import android.support.annotation.ColorInt;

import com.greenkeycompany.exam.FragmentListener;
import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by tert0 on 04.10.2017.
 */

public interface IMainView extends MvpView,
        FragmentListener {

    //void setActionBarColor(@ColorInt int color);
    //void setActionBarTitleTextColor(@ColorInt int color);

    void setMainFragment();
    void setChapterFragment(int chapterId);

    void setRuleFragment(int ruleId);
    void setTrainingMenuFragment(int ruleId);
    void setRuleDescriptionFragment(int ruleId);

    void setWordCardTrainingFragment();
    void setWordCardRuleTrainingFragment(int ruleId);
    void setWordCardRulePointTrainingFragment(int rulePointId);

    void finish();
}
