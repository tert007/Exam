package com.greenkeycompany.exam.fragment.ruledescription.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.fragment.ruledescription.view.IRuleDescriptionView;
import com.greenkeycompany.exam.repository.IRepository;
import com.greenkeycompany.exam.repository.model.RulePoint;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.ArrayList;
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

    @Override
    public void initRule(int ruleId) {
        List<RulePoint> rulePointList = repository.getRulePointList(ruleId);
        for (RulePoint rulePoint : rulePointList) {
            if (isViewAttached()) {
                getView().addRulePointView(rulePoint.getTitle(), rulePoint.getDescription());
            }
        }
    }

    @Override
    public void initRulePoint(int rulePointId) {
        RulePoint rulePoint = repository.getRulePoint(rulePointId);
        if (isViewAttached()) {
            if (isViewAttached()) {
                getView().addRulePointView(rulePoint.getTitle(), rulePoint.getDescription());
            }
        }
    }
}
