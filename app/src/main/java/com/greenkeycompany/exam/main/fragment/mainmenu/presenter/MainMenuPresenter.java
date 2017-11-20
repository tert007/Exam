package com.greenkeycompany.exam.main.fragment.mainmenu.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.app.PremiumUtil;
import com.greenkeycompany.exam.main.FragmentType;
import com.greenkeycompany.exam.main.fragment.mainmenu.model.ChapterMenuItem;
import com.greenkeycompany.exam.main.fragment.mainmenu.view.IMainMenuView;
import com.greenkeycompany.exam.main.repository.model.result.ChapterExamResult;
import com.greenkeycompany.exam.main.repository.IRepository;
import com.greenkeycompany.exam.main.repository.model.Chapter;
import com.greenkeycompany.exam.main.repository.model.result.FinalExamResult;
import com.greenkeycompany.exam.main.util.ChapterUtil;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        FinalExamResult finalExamResult = repository.getBestFinalExamResult();
        if (finalExamResult == null) {
            chapterMenuItemList.add(new ChapterMenuItem(ChapterUtil.FINAL_EXAM_ID, null));
        } else {
            chapterMenuItemList.add(new ChapterMenuItem(ChapterUtil.FINAL_EXAM_ID, null, finalExamResult.getScore()));
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
        if (ChapterUtil.FINAL_EXAM_ID == chapterMenuItem.getId()) {
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
