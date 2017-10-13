package com.greenkeycompany.exam.fragment.mainmenu.presenter;

import com.greenkeycompany.exam.fragment.mainmenu.view.IMainMenuView;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

/**
 * Created by tert0 on 06.10.2017.
 */

public interface IMainMenuPresenter extends MvpPresenter<IMainMenuView> {
    void init();

    void onChapterItemClick(int index);
}
