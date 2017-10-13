package com.greenkeycompany.exam.fragment.chapter.presenter;

import com.greenkeycompany.exam.fragment.chapter.view.IChapterView;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

/**
 * Created by tert0 on 20.09.2017.
 */

public interface IChapterPresenter extends MvpPresenter<IChapterView> {
    void init(int chapterId);

    void onRuleItemClick(int index);
}