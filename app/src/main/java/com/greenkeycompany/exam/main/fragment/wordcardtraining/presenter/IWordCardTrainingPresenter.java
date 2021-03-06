package com.greenkeycompany.exam.main.fragment.wordcardtraining.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.main.TrainingType;
import com.greenkeycompany.exam.main.fragment.wordcardtraining.view.IWordCardTrainingView;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

/**
 * Created by tert0 on 20.09.2017.
 */

public interface IWordCardTrainingPresenter extends MvpPresenter<IWordCardTrainingView> {
    void initTraining(@NonNull TrainingType trainingType, int id);

    void onNextClick();
    void onTrueAnswerClick();
    void onFalseAnswerClick();
}
