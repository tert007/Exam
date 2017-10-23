package com.greenkeycompany.exam.fragment.wordcardresult.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.TrainingType;
import com.greenkeycompany.exam.fragment.TrainingCompletedUtil;
import com.greenkeycompany.exam.fragment.wordcardresult.view.IWordCardResultView;
import com.greenkeycompany.exam.repository.IRepository;
import com.greenkeycompany.exam.repository.model.WordCard;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tert0 on 15.10.2017.
 */

public class WordCardResultPresenter extends MvpBasePresenter<IWordCardResultView>
        implements IWordCardResultPresenter {

    private IRepository repository;
    public WordCardResultPresenter(@NonNull IRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onActionBarRestartPressed(@NonNull TrainingType trainingType, int resultId) {
        switch (trainingType) {
            case RULE_POINT:
                if (isViewAttached()) {
                    getView().requestToRefresh(trainingType, repository.getRulePointResult(resultId).getRulePoint().getId());
                }
                break;
            case RULE:
                if (isViewAttached()) {
                    getView().requestToRefresh(trainingType, repository.getRuleResult(resultId).getRule().getId());
                }
                break;
            case CHAPTER:
                if (isViewAttached()) {
                    getView().requestToRefresh(trainingType, repository.getChapterResult(resultId).getChapter().getId());
                }
                break;
        }
    }

    @Override
    public void init(@NonNull TrainingType trainingType, int resultId, int[] wrongAnswerWordCardIds) {
        /*
        int wrongAnswerCount = wrongAnswerWordCardIds.length;

        List<WordCard> wrongAnswerWordCardList = new ArrayList<>(wrongAnswerCount);
        for (int wordCardId : wrongAnswerWordCardIds) {
            wrongAnswerWordCardList.add(repository.getWordCard(wordCardId));
        }

        switch (trainingType) {
            case RULE_POINT:
                boolean completed = TrainingCompletedUtil.isCompleted(trueAnswerCount, wordCardCount);
                if (isViewAttached()) {
                    getView().setItems(wrongAnswerWordCardList);
                    getView().setCompletedView(completed, trueAnswerCount, wordCardCount);
                }
                break;
        }
        */
    }
}