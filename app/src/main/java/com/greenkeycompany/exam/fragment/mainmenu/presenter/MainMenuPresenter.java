package com.greenkeycompany.exam.fragment.mainmenu.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.fragment.mainmenu.model.ChapterMenuItem;
import com.greenkeycompany.exam.fragment.mainmenu.view.IMainMenuView;
import com.greenkeycompany.exam.repository.IRepository;
import com.greenkeycompany.exam.repository.model.Chapter;
import com.greenkeycompany.exam.repository.model.result.ChapterExamResult;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tert0 on 06.10.2017.
 */

public class MainMenuPresenter extends MvpBasePresenter<IMainMenuView>
        implements IMainMenuPresenter {

    private List<ChapterMenuItem> chapterMenuItemList;

    public MainMenuPresenter(@NonNull IRepository repository) {
        List<Chapter> chapterList = repository.getChapterList();

        this.chapterMenuItemList = new ArrayList<>(chapterList.size());
        for (Chapter chapter: chapterList) {
            ChapterExamResult bestResult = repository.getBestChapterExamResult(chapter.getId());
            if (bestResult == null) {
                chapterMenuItemList.add(new ChapterMenuItem(chapter.getId(), chapter.getTitle()));
            } else {
                chapterMenuItemList.add(new ChapterMenuItem(chapter.getId(), chapter.getTitle(), bestResult.getScore()));
            }
        }
    }

    @Override
    public void init() {
        if (isViewAttached()) {
            getView().setChapters(chapterMenuItemList);
        }
    }

    @Override
    public void onChapterItemClick(int index) {
        if (isViewAttached()) {
            getView().requestToSetChapterFragment(chapterMenuItemList.get(index).getId());
        }
    }
}
