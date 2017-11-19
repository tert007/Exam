package com.greenkeycompany.exam.main.fragment.ruledetail.presenter;

import com.greenkeycompany.exam.main.fragment.ruledetail.view.IRuleDetailView;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

/**
 * Created by tert0 on 20.09.2017.
 */

public interface IRuleDetailPresenter extends MvpPresenter<IRuleDetailView> {
    void init(int ruleId);

    void onDescriptionViewClick();
    void onTrainingViewClick();
    void onRuleExamViewClick();
}
