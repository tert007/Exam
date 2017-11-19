package com.greenkeycompany.exam.main.fragment.ruledetail.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.main.util.ChapterColorUtil;
import com.greenkeycompany.exam.main.fragment.ruledetail.view.IRuleDetailView;
import com.greenkeycompany.exam.main.repository.model.result.RuleExamResult;
import com.greenkeycompany.exam.main.repository.IRepository;
import com.greenkeycompany.exam.main.repository.model.Rule;
import com.greenkeycompany.exam.main.repository.model.WordCardSet;
import com.greenkeycompany.exam.main.repository.model.status.RuleStatus;
import com.greenkeycompany.exam.main.repository.model.status.WordCardSetStatus;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.List;

/**
 * Created by tert0 on 20.09.2017.
 */

public class RuleDetailPresenter extends MvpBasePresenter<IRuleDetailView>
        implements IRuleDetailPresenter {

    private IRepository repository;
    public RuleDetailPresenter(@NonNull IRepository repository) {
        this.repository = repository;
    }

    private boolean isTrainingCompleted(@NonNull List<WordCardSet> wordCardSetList) {
        for (WordCardSet wordCardSet: wordCardSetList) {
            WordCardSetStatus status = repository.getWordCardSetStatus(wordCardSet.getId());

            if (status == null || ! status.isCompleted()) {
                return false;
            }
        }

        return true;
    }

    private int ruleId;

    private boolean learned;
    private boolean trainingCompleted;

    @Override
    public void init(int ruleId) {
        this.ruleId = ruleId;

        Rule rule = repository.getRule(ruleId);
        RuleStatus ruleStatus = repository.getRuleStatus(ruleId);

        learned = ruleStatus != null && ruleStatus.isLearned();

        List<WordCardSet> wordCardSetList = repository.getWordCardSetList(ruleId);
        trainingCompleted = isTrainingCompleted(wordCardSetList);

        if (isViewAttached()) {
            getView().requestToSetActionBarTitle(rule.getTitle());
            getView().setBackgroundColor(ChapterColorUtil.getColor(rule.getChapterId()));
            getView().setRuleDescriptionCompleted(learned);
            getView().setRuleTrainingCompleted(trainingCompleted);
        }

        if (trainingCompleted) {
            RuleExamResult bestResult = repository.getBestRuleExamResult(ruleId);
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
        if (learned) {
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
