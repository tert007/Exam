package com.greenkeycompany.exam.fragment.mainmenu.view;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.fragment.mainmenu.model.ChapterMenuItem;
import com.greenkeycompany.exam.repository.model.Chapter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

/**
 * Created by tert0 on 06.10.2017.
 */

public interface IMainMenuView extends MvpView {
    void setChapters(@NonNull List<ChapterMenuItem> chapterList);

    void requestToSetFinalExamFragment();
    void requestToSetChapterFragment(int chapterId);
}
