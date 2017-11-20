package com.greenkeycompany.exam.main.fragment.ruledescription.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.main.fragment.ruledescription.view.IRuleDescriptionView;
import com.greenkeycompany.exam.main.repository.IRepository;
import com.greenkeycompany.exam.main.repository.model.Rule;
import com.greenkeycompany.exam.main.repository.model.RulePoint;
import com.greenkeycompany.exam.main.repository.model.status.RuleStatus;
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

    @Override
    public void init(int ruleId) {
        this.ruleId = ruleId;

        Rule rule = repository.getRule(ruleId);
        RuleStatus ruleStatus = repository.getRuleStatus(ruleId);

        boolean learned = ruleStatus != null && ruleStatus.isLearned();
        if (isViewAttached()) {
            getView().setRuleTitleView(rule.getTitle());
            getView().setLearnViewVisibility( ! learned);
        }

        List<RulePoint> rulePointList = repository.getRulePointList(ruleId);
        for (RulePoint rulePoint : rulePointList) {
            if (isViewAttached()) {
                getView().addRulePointView(rulePoint.getTitle(), rulePoint.getDescription());
            }
        }
    }

    @Override
    public void onLearnViewClick() {
        repository.addOrUpdateRuleStatus(ruleId, true);
    }
}
