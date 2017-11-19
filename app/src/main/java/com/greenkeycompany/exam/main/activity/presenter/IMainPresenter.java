package com.greenkeycompany.exam.main.activity.presenter;

import com.greenkeycompany.exam.main.activity.view.FragmentListener;
import com.greenkeycompany.exam.main.activity.view.IMainView;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

/**
 * Created by tert0 on 04.10.2017.
 */

public interface IMainPresenter extends MvpPresenter<IMainView>, FragmentListener {

    void onBackPressed();
}
