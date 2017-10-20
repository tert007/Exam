package com.greenkeycompany.exam.fragment.wordcardresult.view;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.repository.model.WordCard;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

/**
 * Created by tert0 on 14.10.2017.
 */

public interface IWordCardResultView extends MvpView {

    void setCompletedView(boolean completed, int wordCardCompletedCount, int wordCardCount);

    void setItems(@NonNull List<WordCard> wordCardList);

    void requestToSetWordCardRulePointFragment(int rulePointId);
}
