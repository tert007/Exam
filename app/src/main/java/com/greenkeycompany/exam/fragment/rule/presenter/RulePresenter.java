package com.greenkeycompany.exam.fragment.rule.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.fragment.ChapterColorUtil;
import com.greenkeycompany.exam.fragment.rule.view.IRuleView;
import com.greenkeycompany.exam.repository.IRepository;
import com.greenkeycompany.exam.repository.model.Rule;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

/**
 * Created by tert0 on 20.09.2017.
 */

public class RulePresenter extends MvpBasePresenter<IRuleView>
        implements IRulePresenter {

    private IRepository repository;
    public RulePresenter(@NonNull IRepository repository) {
        this.repository = repository;
    }

    @Override
    public void init(int ruleId) {
        int chapterId = repository.getRule(ruleId).getChapter().getId();
        if (isViewAttached()) {
            getView().setBackgroundColor(ChapterColorUtil.getColor(chapterId));
        }
    }
}
