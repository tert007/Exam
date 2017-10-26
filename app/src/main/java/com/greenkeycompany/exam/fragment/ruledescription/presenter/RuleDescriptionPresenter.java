package com.greenkeycompany.exam.fragment.ruledescription.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.fragment.ruledescription.view.IRuleDescriptionView;
import com.greenkeycompany.exam.repository.IRepository;
import com.greenkeycompany.exam.repository.model.Rule;
import com.greenkeycompany.exam.repository.model.RulePoint;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.List;

/**
 * Created by tert0 on 05.10.2017.
 */

public class RuleDescriptionPresenter extends MvpBasePresenter<IRuleDescriptionView>
        implements IRuleDescriptionPresenter {

    private IRepository repository;
    public RuleDescriptionPresenter(@NonNull IRepository repository) {
        this.repository = repository;
    }

    private int ruleId;
    private Rule rule;

    @Override
    public void init(int ruleId) {
        this.ruleId = ruleId;
        this.rule = repository.getRule(ruleId);
        if (isViewAttached()) {
            getView().setRuleTitleView(rule.getTitle());
            getView().setCompletedButtonVisibility( ! rule.isDescriptionCompleted());
        }

        List<RulePoint> rulePointList = repository.getRulePointList(ruleId);
        for (RulePoint rulePoint : rulePointList) {
            if (isViewAttached()) {
                getView().addRulePointView(rulePoint.getTitle(), rulePoint.getDescription());
            }
        }
    }

    @Override
    public void onCompletedButtonClick() {
        repository.updateRule(ruleId, true);
        if (isViewAttached()) {
            getView().setCompletedButtonVisibility(false);
        }
    }
}
