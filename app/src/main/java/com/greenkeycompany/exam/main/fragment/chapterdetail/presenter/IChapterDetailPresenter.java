package com.greenkeycompany.exam.main.fragment.chapterdetail.presenter;

import com.greenkeycompany.exam.main.fragment.chapterdetail.view.IChapterDetailView;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

/**
 * Created by tert0 on 20.09.2017.
 */

public interface IChapterDetailPresenter extends MvpPresenter<IChapterDetailView> {
    void init(int chapterId);

    void onStartChapterTrainingClick();

    void onRuleMenuItemClick(int index);
}
