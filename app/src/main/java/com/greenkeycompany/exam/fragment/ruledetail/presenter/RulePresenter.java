package com.greenkeycompany.exam.fragment.ruledetail.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.fragment.ChapterColorUtil;
import com.greenkeycompany.exam.fragment.ruledetail.view.IRuleView;
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
            if ( ! rulePoint.isTrainingCompleted()) return false;
        }

        return true;
    }

    private int ruleId;

    private boolean descriptionCompleted;
    private boolean trainingCompleted;

    @Override
    public void init(int ruleId) {
        this.ruleId = ruleId;

        Rule rule = repository.getRule(ruleId);
        List<RulePoint> rulePointList = repository.getRulePointList(ruleId);

        descriptionCompleted = rule.isDescriptionCompleted();
        trainingCompleted = isTrainingCompleted(rulePointList);
        if (isViewAttached()) {
            getView().setBackgroundColor(ChapterColorUtil.getColor(rule.getChapter().getId()));
            getView().setRuleDescriptionCompleted(descriptionCompleted);
            getView().setRuleTrainingCompleted(trainingCompleted);
        }

        if (trainingCompleted) {
            RuleResult bestResult = repository.getBestRuleResult(ruleId);
            if (isViewAttached()) {
                getView().setRuleExamCompleted(bestResult == null ? 0 : bestResult.getScore());
            }
        } else {
            if (isViewAttached()) {
                getView().setRuleExamUncompleted();
            }
        }
    }

    @Override
    public void onDescriptionViewClick() {
        if (isViewAttached()) {
            getView().requestToShowDescription(ruleId);
        }
    }

    @Override
    public void onTrainingViewClick() {
        if (descriptionCompleted) {
            if (isViewAttached()) {
                getView().requestToShowTraining(ruleId);
            }
        } else {
            if (isViewAttached()) {
                getView().showTrainingLockedDialog();
            }
        }
    }

    @Override
    public void onRuleExamViewClick() {
        if (trainingCompleted) {
            if (isViewAttached()) {
                getView().requestToShowRuleExam(ruleId);
            }
        } else {
            if (isViewAttached()) {
                getView().showRuleExamLockedDialog();
            }
        }
    }
}
