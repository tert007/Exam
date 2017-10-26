package com.greenkeycompany.exam.fragment.ruledetail.view;

import android.support.annotation.ColorInt;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by tert0 on 20.09.2017.
 */

public interface IRuleView extends MvpView {
    void setBackgroundColor(@ColorInt int color);

    void setRuleDescriptionCompleted(boolean completed);
    void setRuleTrainingCompleted(boolean completed);

    void setRuleExamUncompleted();
    void setRuleExamCompleted(float score);

    void showTrainingLockedDialog();
    void showRuleExamLockedDialog();

    void requestToShowDescription(int ruleId);
    void requestToShowTraining(int ruleId);
    void requestToShowRuleExam(int ruleId);
}
