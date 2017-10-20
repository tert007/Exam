package com.greenkeycompany.exam.fragment.wordcardresult.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.TrainingType;
import com.greenkeycompany.exam.fragment.wordcardresult.view.IWordCardResultView;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

/**
 * Created by tert0 on 14.10.2017.
 */

public interface IWordCardResultPresenter extends MvpPresenter<IWordCardResultView> {
    void init(@NonNull TrainingType trainingType, int id, int wordCardCount, int[] wrongAnswerWordCardIds);
}
