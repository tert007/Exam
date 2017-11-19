package com.greenkeycompany.exam.fragment.mainmenu.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.fragment.mainmenu.model.ChapterMenuItem;
import com.greenkeycompany.exam.fragment.mainmenu.view.IMainMenuView;
import com.greenkeycompany.exam.repository.IRepository;
import com.greenkeycompany.exam.repository.model.Chapter;
import com.greenkeycompany.exam.repository.model.result.ChapterExamResult;
import com.greenkeycompany.exam.repository.model.result.FinalExamResult;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tert0 on 06.10.2017.
 */

public class MainMenuPresenter extends MvpBasePresenter<IMainMenuView>
        implements IMainMenuPresenter {

    public static final int FINAL_EXAM_ITEM_ID = 0;

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

        FinalExamResult finalExamResult = repository.getBestFinalExamResult();
        if (finalExamResult == null) {
            chapterMenuItemList.add(new ChapterMenuItem(FINAL_EXAM_ITEM_ID, null));
        } else {
            chapterMenuItemList.add(new ChapterMenuItem(FINAL_EXAM_ITEM_ID, null, finalExamResult.getScore()));
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
        final ChapterMenuItem chapterMenuItem = chapterMenuItemList.get(index);
        if (chapterMenuItem.getId() == FINAL_EXAM_ITEM_ID) {
            if (isViewAttached()) {
                getView().requestToSetFinalExamFragment();
            }
        } else {
            if (isViewAttached()) {
                getView().requestToSetChapterFragment(chapterMenuItem.getId());
            }
        }
    }
}
