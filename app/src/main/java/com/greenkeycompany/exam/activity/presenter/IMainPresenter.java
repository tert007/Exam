package com.greenkeycompany.exam.activity.presenter;

import com.greenkeycompany.exam.FragmentListener;
import com.greenkeycompany.exam.FragmentType;
import com.greenkeycompany.exam.activity.view.IMainView;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

/**
 * Created by tert0 on 04.10.2017.
 */

public interface IMainPresenter extends MvpPresenter<IMainView>,
        FragmentListener {

    void onBackPressed();
}
