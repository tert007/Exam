package com.greenkeycompany.exam.main.fragment.chapterdetail.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.main.util.ChapterColorUtil;
import com.greenkeycompany.exam.main.util.ScoreUtil;
import com.greenkeycompany.exam.main.fragment.chapterdetail.model.RuleMenuItem;
import com.greenkeycompany.exam.main.fragment.chapterdetail.view.IChapterDetailView;
import com.greenkeycompany.exam.main.repository.model.result.ChapterExamResult;
import com.greenkeycompany.exam.main.repository.model.result.RuleExamResult;
import com.greenkeycompany.exam.main.repository.IRepository;
import com.greenkeycompany.exam.main.repository.model.Chapter;
import com.greenkeycompany.exam.main.repository.model.WordCardSet;
import com.greenkeycompany.exam.main.repository.model.Rule;
import com.greenkeycompany.exam.main.repository.model.result.WordCardSetResult;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tert0 on 20.09.2017.
 */

public class ChapterDetailPresenter extends MvpBasePresenter<IChapterDetailView>
        implements IChapterDetailPresenter {

    private IRepository repository;
    public ChapterDetailPresenter(@NonNull IRepository repository) {
        this.repository = repository;
    }

    private int chapterId;
    private boolean chapterTrainingLocked;

    private List<Rule> ruleList;
    private List<RuleMenuItem> ruleMenuItemList;

    @Override
    public void init(int chapterId) {
        this.chapterId = chapterId;
        this.chapterTrainingLocked = false;

        ruleList = repository.getRuleList(chapterId);
        ruleMenuItemList = new ArrayList<>(ruleList.size());

        int chapterWordCardCount = 0;
        int chapterWordCardCompletedCount = 0;

        for (Rule rule : ruleList) {
            String title = rule.getTitle();

            RuleExamResult bestResult = repository.getBestRuleExamResult(rule.getId());
            float bestScore = bestResult == null ? 0 : bestResult.getScore();

            if (bestScore < ScoreUtil.COMPLETED_SCORE) chapterTrainingLocked = true;

            int wordCardCount = 0;
            int wordCardCompletedCount = 0;

            for (WordCardSet wordCardSet : repository.getWordCardSetList(rule.getId())) {
                wordCardCount += wordCardSet.getSize();

                WordCardSetResult wordCardSetResult = repository.getBestWordCardSetResult(wordCardSet.getId());
                wordCardCompletedCount += wordCardSetResult != null ? wordCardSetResult.getWordCardCompletedCount() : 0;
            }

            chapterWordCardCount += wordCardCount;
            chapterWordCardCompletedCount += wordCardCompletedCount;

            ruleMenuItemList.add(new RuleMenuItem(title, bestScore, wordCardCount, wordCardCompletedCount));
        }

        Chapter chapter = repository.getChapter(chapterId);
        ChapterExamResult bestChapterResult = repository.getBestChapterExamResult(chapterId);
        if (isViewAttached()) {
            getView().requestToSetActionBarTitle(chapter.getTitle());
            getView().setChapterViewColor(ChapterColorUtil.getColor(chapterId));
            getView().setChapterScore(bestChapterResult == null ? 0 : bestChapterResult.getScore());
            getView().setChapterWordCardCompletedCount(chapterWordCardCompletedCount,chapterWordCardCount);
            getView().setRuleItemList(ruleMenuItemList);
        }
    }

    @Override
    public void onStartChapterTrainingClick() {
        if (chapterTrainingLocked) {
            if (isViewAttached()) {
                getView().showChapterTrainingLockedDialog();
            }
        } else {
            if (isViewAttached()) {
                getView().requestToStartChapterTraining(chapterId);
            }
        }
    }

    @Override
    public void onRuleMenuItemClick(int index) {
        if (isViewAttached()) {
            getView().requestToSetRuleDetailFragment(ruleList.get(index).getId());
        }
    }
}
