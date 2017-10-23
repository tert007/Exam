package com.greenkeycompany.exam.fragment.wordcardresult.view;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.TrainingType;
import com.greenkeycompany.exam.repository.model.WordCard;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

/**
 * Created by tert0 on 14.10.2017.
 */

public interface IWordCardResultView extends MvpView {
    void requestToRefresh(@NonNull TrainingType trainingType, int trainingId);
}
