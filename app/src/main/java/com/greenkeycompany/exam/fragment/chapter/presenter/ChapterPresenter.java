package com.greenkeycompany.exam.fragment.chapter.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.fragment.ScoreUtil;
import com.greenkeycompany.exam.fragment.chapter.model.RuleMenuItem;
import com.greenkeycompany.exam.fragment.chapter.view.IChapterView;
import com.greenkeycompany.exam.repository.IRepository;
import com.greenkeycompany.exam.repository.model.Rule;
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
            float bestScore = bestResult == null ? ScoreUtil.MIN_SCORE : bestResult.getScore();

            int wordCardCount = repository.getWordCardCountByRule(rule.getId());
            int wordCardCompletedCount = 0;
            for (RulePoint rulePoint : repository.getRulePointList(rule.getId())) {
                wordCardCompletedCount = rulePoint.getWordCardCompletedCount();
            }

            ruleMenuItemList.add(new RuleMenuItem(title, bestScore, wordCardCount, wordCardCompletedCount));
        }

        if (isViewAttached()) {
            getView().setItemList(ruleMenuItemList);
        }
    }

    @Override
    public void onRuleMenuItemClick(int index) {
        if (isViewAttached()) {
            getView().requestToSetRuleFragment(ruleList.get(index).getId());
        }
    }
}
