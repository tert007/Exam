package com.greenkeycompany.exam.fragment.chapterdetail.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.fragment.ChapterColorUtil;
import com.greenkeycompany.exam.fragment.chapterdetail.model.RuleMenuItem;
import com.greenkeycompany.exam.fragment.chapterdetail.view.IChapterView;
import com.greenkeycompany.exam.repository.IRepository;
import com.greenkeycompany.exam.repository.model.Rule;
import com.greenkeycompany.exam.repository.model.RulePointResult;
import com.greenkeycompany.exam.repository.model.RuleResult;
import com.greenkeycompany.exam.repository.model.RulePoint;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tert0 on 20.09.2017.
 */

public class ChapterPresenter extends MvpBasePresenter<IChapterView>
        implements IChapterPresenter {

    private IRepository repository;
    public ChapterPresenter(@NonNull IRepository repository) {
        this.repository = repository;
    }

    private List<Rule> ruleList;
    private List<RuleMenuItem> ruleMenuItemList;

    @Override
    public void init(int chapterId) {
        ruleList = repository.getRuleList(chapterId);
        ruleMenuItemList = new ArrayList<>(ruleList.size());

        for (Rule rule : ruleList) {
            String title = rule.getTitle();

            RuleResult bestResult = repository.getBestRuleResult(rule.getId());
            float bestScore = bestResult == null ? 0 : bestResult.getScore();

            int wordCardCount = 0;
            int wordCardCompletedCount = 0;

            for (RulePoint rulePoint : repository.getRulePointList(rule.getId())) {
                wordCardCount += rulePoint.getWordCardTrainingCount();

                RulePointResult rulePointResult = repository.getBestRulePointResult(rulePoint.getId());
                wordCardCompletedCount += rulePointResult != null ? rulePointResult.getWordCardCompletedCount() : 0;
            }

            ruleMenuItemList.add(new RuleMenuItem(title, bestScore, wordCardCount, wordCardCompletedCount));
        }

        if (isViewAttached()) {
            getView().setRuleItemList(ruleMenuItemList);
            getView().setChapterDescriptionViewColor(ChapterColorUtil.getColor(chapterId));
        }
    }

    @Override
    public void onRuleMenuItemClick(int index) {
        if (isViewAttached()) {
            getView().requestToSetRuleDetailFragment(ruleList.get(index).getId());
        }
    }
}
