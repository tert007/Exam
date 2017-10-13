package com.greenkeycompany.exam.fragment.mainmenu.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.fragment.mainmenu.view.IMainMenuView;
import com.greenkeycompany.exam.repository.IRepository;
import com.greenkeycompany.exam.repository.model.Chapter;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tert0 on 06.10.2017.
 */

public class MainMenuPresenter extends MvpBasePresenter<IMainMenuView>
        implements IMainMenuPresenter {

    private List<Chapter> chapterList;
    public MainMenuPresenter(@NonNull IRepository repository) {
        this.chapterList = repository.getChapterList();
    }

    @Override
    public void init() {
        if (isViewAttached()) {
            getView().setChapters(chapterList);
        }
    }

    @Override
    public void onChapterItemClick(int index) {
        if (isViewAttached()) {
            getView().requestToSetChapterFragment(chapterList.get(index).getId());
        }
    }
}
