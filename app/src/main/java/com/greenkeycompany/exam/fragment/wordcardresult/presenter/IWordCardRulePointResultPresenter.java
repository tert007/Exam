package com.greenkeycompany.exam.fragment.wordcardresult.presenter;

import com.greenkeycompany.exam.fragment.wordcardresult.view.IWordCardRulePointResultView;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

/**
 * Created by tert0 on 14.10.2017.
 */

public interface IWordCardRulePointResultPresenter extends MvpPresenter<IWordCardRulePointResultView> {
    void init(int rulePointId, int wordCardCount, int[] wrongAnswerWordCardIds);
}
