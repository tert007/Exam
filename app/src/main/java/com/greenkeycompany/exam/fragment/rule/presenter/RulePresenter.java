package com.greenkeycompany.exam.fragment.rule.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.fragment.ChapterColorUtil;
import com.greenkeycompany.exam.fragment.ScoreUtil;
import com.greenkeycompany.exam.fragment.rule.view.IRuleView;
import com.greenkeycompany.exam.repository.IRepository;
import com.greenkeycompany.exam.repository.model.Rule;
import com.greenkeycompany.exam.repository.model.RulePoint;
import com.greenkeycompany.exam.repository.model.RuleResult;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.List;

/**
 * Created by tert0 on 20.09.2017.
 */

public class RulePresenter extends MvpBasePresenter<IRuleView>
        implements IRulePresenter {

    private IRepository repository;
    public RulePresenter(@NonNull IRepository repository) {
        this.repository = repository;
    }

    private boolean isTrainingCompleted(@NonNull List<RulePoint> rulePointList) {
        for (RulePoint rulePoint: rulePointList) {
            if ( ! rulePoint.isCompleted()) return false;
        }

        return true;
    }

    @Override
    public void init(int ruleId) {
        Rule rule = repository.getRule(ruleId);
        List<RulePoint> rulePointList = repository.getRulePointList(ruleId);

        boolean trainingCompleted = isTrainingCompleted(rulePointList);
        if (isViewAttached()) {
            getView().setBackgroundColor(ChapterColorUtil.getColor(rule.getChapter().getId()));
            getView().setRuleDescriptionCompleted(rule.isDescriptionCompleted());
            getView().setRuleTrainingCompleted(trainingCompleted);
        }

        if (trainingCompleted) {
            RuleResult ruleResult = repository.getBestRuleExamResult(ruleId);
            if (isViewAttached()) {
                getView().setRuleExamCompleted(ruleResult == null ?
                        ScoreUtil.MIN_SCORE :
                        ruleResult.getScore());
            }
        } else {
            if (isViewAttached()) {
                getView().setRuleExamUncompleted();
            }
        }
    }
}
