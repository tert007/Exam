package com.greenkeycompany.exam.main.fragment.trainingmenu.presenter;

import com.greenkeycompany.exam.main.fragment.trainingmenu.view.ITrainingMenuView;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

/**
 * Created by tert0 on 06.10.2017.
 */

public interface ITrainingMenuPresenter extends MvpPresenter<ITrainingMenuView> {
    void init(int ruleId);
    void onTrainingItemClick(int index);
}
