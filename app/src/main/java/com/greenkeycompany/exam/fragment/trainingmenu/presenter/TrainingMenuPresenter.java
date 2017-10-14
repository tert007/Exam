package com.greenkeycompany.exam.fragment.trainingmenu.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.fragment.ChapterColorUtil;
import com.greenkeycompany.exam.fragment.trainingmenu.model.WordCardMenuItem;
import com.greenkeycompany.exam.fragment.trainingmenu.view.ITrainingMenuView;
import com.greenkeycompany.exam.repository.IRepository;
import com.greenkeycompany.exam.repository.model.RulePoint;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tert0 on 06.10.2017.
 */

public class TrainingMenuPresenter extends MvpBasePresenter<ITrainingMenuView>
        implements ITrainingMenuPresenter {

    private IRepository repository;
    public TrainingMenuPresenter(@NonNull IRepository repository) {
        this.repository = repository;
    }

    private List<RulePoint> rulePointList;

    @Override
    public void init(int ruleId) {
        this.rulePointList = repository.getRulePointList(ruleId);

        List<WordCardMenuItem> wordCardMenuItemList = new ArrayList<>();
        for (RulePoint rulePoint : rulePointList) {
            long wordCardCount = repository.getWordCardCount(rulePoint.getId());

            wordCardMenuItemList.add(new WordCardMenuItem(rulePoint, wordCardCount));
        }

        int chapterId = repository.getRule(ruleId).getChapter().getId();
        if (isViewAttached()) {
            getView().setBackgroundColor(ChapterColorUtil.getColor(chapterId));
            getView().setTrainingModelItems(wordCardMenuItemList);
        }
    }

    @Override
    public void onTrainingItemClick(int index) {
        if (isViewAttached()) {
            getView().requestToSetWordCardRulePointTrainingFragment(rulePointList.get(index).getId());
        }
    }
}
