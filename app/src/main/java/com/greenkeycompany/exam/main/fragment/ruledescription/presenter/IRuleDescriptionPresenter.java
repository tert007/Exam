package com.greenkeycompany.exam.main.fragment.ruledescription.presenter;

import com.greenkeycompany.exam.main.fragment.ruledescription.view.IRuleDescriptionView;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

/**
 * Created by tert0 on 05.10.2017.
 */

public interface IRuleDescriptionPresenter extends MvpPresenter<IRuleDescriptionView> {
    void init(int ruleId);

    void onLearnViewClick();
}
