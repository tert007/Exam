package com.greenkeycompany.exam.main.fragment.wordcardtraining.view;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.main.TrainingType;
import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by tert0 on 20.09.2017.
 */

public interface IWordCardTrainingView extends MvpView {

    void initProgressView(int itemCount);
    void setProgressViewItem(int index, boolean trueAnswer);
    
    void setScoreView(int wordCardCount, int trueAnswerCount);
    void setScoreViewVisibility(boolean visible);

    void setWordView(String word);
    void setWordResultView(boolean trueAnswer);

    void setIncorrectWordView(String correctWord);
    void setIncorrectWordViewVisibility(boolean visible);
    void setCorrectWordViewVisibility(boolean visible);

    void setNextViewVisibility(boolean visible);
    void setAnswersButtonVisibility(boolean visible);

    void requestToSetResultFragment(@NonNull TrainingType trainingType, int resultId, int[] wrongAnswerWordCardIds);
}
