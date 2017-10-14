package com.greenkeycompany.exam.fragment.chapter.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.fragment.chapter.view.IChapterView;
import com.greenkeycompany.exam.repository.IRepository;
import com.greenkeycompany.exam.repository.model.Rule;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

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

    @Override
    public void init(int chapterId) {
        this.ruleList = repository.getRuleList(chapterId);
        if (isViewAttached()) {
            getView().setItemList(ruleList);
        }
    }

    @Override
    public void onRuleItemClick(int index) {
        if (isViewAttached()) {
            getView().requestToSetRuleFragment(ruleList.get(index).getId());
        }
    }
}
