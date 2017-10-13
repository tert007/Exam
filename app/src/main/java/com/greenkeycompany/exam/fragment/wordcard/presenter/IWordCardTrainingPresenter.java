package com.greenkeycompany.exam.fragment.wordcard.presenter;

import com.greenkeycompany.exam.fragment.wordcard.view.IWordCardTrainingView;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

/**
 * Created by tert0 on 20.09.2017.
 */

public interface IWordCardTrainingPresenter extends MvpPresenter<IWordCardTrainingView> {

    void initTraining();
    void initRuleTraining(int ruleId);
    void initRulePointTraining(int rulePointId);

    void onNextClick();
    void onTrueAnswerClick();
    void onFalseAnswerClick();
}
