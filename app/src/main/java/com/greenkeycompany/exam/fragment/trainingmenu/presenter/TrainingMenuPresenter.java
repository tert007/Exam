package com.greenkeycompany.exam.fragment.trainingmenu.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.fragment.ChapterColorUtil;
import com.greenkeycompany.exam.fragment.trainingmenu.model.WordCardMenuItem;
import com.greenkeycompany.exam.fragment.trainingmenu.view.ITrainingMenuView;
import com.greenkeycompany.exam.repository.IRepository;
import com.greenkeycompany.exam.repository.model.WordCardSet;
import com.greenkeycompany.exam.repository.model.result.WordCardSetResult;
import com.greenkeycompany.exam.repository.model.status.WordCardSetStatus;
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

    private List<WordCardSet> wordCardSetList;

    @Override
    public void init(int ruleId) {
        this.wordCardSetList = repository.getWordCardSetList(ruleId);

        List<WordCardMenuItem> wordCardMenuItemList = new ArrayList<>();
        for (WordCardSet wordCardSet : wordCardSetList) {
            WordCardSetResult bestResult = repository.getBestWordCardSetResult(wordCardSet.getId());
            int wordCardCompletedCount = bestResult != null ? bestResult.getWordCardCompletedCount() : 0;

            WordCardSetStatus status = repository.getWordCardSetStatus(wordCardSet.getId());
            boolean completed = status != null && status.isCompleted();

            WordCardMenuItem wordCardMenuItem = new WordCardMenuItem();

            wordCardMenuItem.setWordCardCount(wordCardSet.getSize());
            wordCardMenuItem.setCompleted(completed);
            wordCardMenuItem.setWordCardCompletedCount(wordCardCompletedCount);

            wordCardMenuItemList.add(wordCardMenuItem);
        }

        int chapterId = repository.getRule(ruleId).getChapterId();
        if (isViewAttached()) {
            getView().setBackgroundColor(ChapterColorUtil.getColor(chapterId));
            getView().setTrainingModelItems(wordCardMenuItemList);
        }
    }

    @Override
    public void onTrainingItemClick(int index) {
        if (isViewAttached()) {
            getView().requestToSetWordCardRulePointTrainingFragment(wordCardSetList.get(index).getId());
        }
    }
}
