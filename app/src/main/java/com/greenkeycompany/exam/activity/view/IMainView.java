package com.greenkeycompany.exam.activity.view;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.FragmentListener;
import com.greenkeycompany.exam.FragmentType;
import com.greenkeycompany.exam.TrainingType;
import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by tert0 on 04.10.2017.
 */

public interface IMainView extends MvpView, ActionBarView, FragmentListener {

    void setMainFragment();
    void setChapterDetailFragment(int chapterId);
    void setRuleDetailFragment(int ruleId);
    void setTrainingMenuFragment(int ruleId);
    void setRuleDescriptionFragment(int ruleId);

    void setWordCardTrainingFragment(@NonNull TrainingType trainingType, int id);
    void setWordCardResultFragment(@NonNull TrainingType trainingType, int resultId, int[] wrongAnswerWordCardIds);

    void backStack(FragmentType fragmentType);
    void backStack();

    void finish();
}
