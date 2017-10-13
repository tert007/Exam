package com.greenkeycompany.exam.fragment.rule.presenter;

import com.greenkeycompany.exam.fragment.rule.view.IRuleView;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

/**
 * Created by tert0 on 20.09.2017.
 */

public interface IRulePresenter extends MvpPresenter<IRuleView> {
    void init(int ruleId);
}
