package com.greenkeycompany.exam.activity.view;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import com.greenkeycompany.exam.FragmentListener;
import com.greenkeycompany.exam.FragmentType;
import com.greenkeycompany.exam.TrainingType;
import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by tert0 on 04.10.2017.
 */

public interface IMainView extends MvpView,
        FragmentListener {

    void setActionBarHomeButtonVisibility(boolean visible);

    void setMainFragment();
    void setChapterFragment(int chapterId);

    void setRuleFragment(int ruleId);
    void setTrainingMenuFragment(int ruleId);
    void setRuleDescriptionFragment(int ruleId);

    void setWordCardTrainingFragment(@NonNull TrainingType trainingType, int id);
    void setWordCardResultFragment(@NonNull TrainingType trainingType, int id, int wordCardCount, int[] wrongAnswerWordCardIds);

    void backStack(FragmentType fragmentType);
    void backStack();

    void finish();
}
