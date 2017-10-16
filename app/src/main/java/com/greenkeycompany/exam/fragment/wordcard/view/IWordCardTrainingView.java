package com.greenkeycompany.exam.fragment.wordcard.view;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by tert0 on 20.09.2017.
 */

public interface IWordCardTrainingView extends MvpView {
    void initProgressView(int itemCount);
    void setProgressViewItem(int index, boolean trueAnswer);
    
    void setScoreView(int wordCardCount, int trueAnswerCount);

    void setWordView(String word);
    void setWordResultView(boolean trueAnswer);

    void setCorrectWordView(String correctWord);
    void setCorrectWordViewVisibility(boolean visible);

    void setNextButtonVisibility(boolean visible);
    void setAnswersButtonVisibility(boolean visible);

    void requestToSetRuleResultFragment(int ruleId, int wordCardCount, int[] wrongAnswerWordCardIds);
    void requestToSetRulePointResultFragment(int rulePointId, int wordCardCount, int[] wrongAnswerWordCardIds);
}
