package com.greenkeycompany.exam.fragment.wordcardresult.view;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by tert0 on 14.10.2017.
 */

public interface IWordCardRulePointResultView extends MvpView {
    void requestToSetWordCardRulePointFragment(int rulePointId);
}
