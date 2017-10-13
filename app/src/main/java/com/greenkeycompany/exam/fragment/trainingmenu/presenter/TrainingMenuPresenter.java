package com.greenkeycompany.exam.fragment.trainingmenu.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.fragment.ChapterColorUtil;
import com.greenkeycompany.exam.fragment.trainingmenu.model.TrainingMenuModel;
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

    private List<TrainingMenuModel> trainingMenuModelList;

    @Override
    public void init(int ruleId) {
        trainingMenuModelList = new ArrayList<>();

        List<RulePoint> rulePointList = repository.getRulePointList(ruleId);
        for (RulePoint rulePoint : rulePointList) {
            trainingMenuModelList.add(new TrainingMenuModel(rulePoint.getId()));
        }

        int chapterId = repository.getRule(ruleId).getChapter().getId();
        if (isViewAttached()) {
            getView().setBackgroundColor(ChapterColorUtil.getColor(chapterId));
            getView().setTrainingModelItems(trainingMenuModelList);
        }
    }

    @Override
    public void onTrainingItemClick(int index) {
        if (isViewAttached()) {
            getView().requestToSetWordCardRulePointTrainingFragment(trainingMenuModelList.get(index).getRulePointId());
        }
    }
}
