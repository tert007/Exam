package com.greenkeycompany.exam.fragment.wordcard.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.repository.IRepository;
import com.greenkeycompany.exam.repository.model.WordCard;
import com.greenkeycompany.exam.fragment.wordcard.view.IWordCardTrainingView;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.List;
import java.util.Random;

/**
 * Created by tert0 on 20.09.2017.
 */

public class WordCardTrainingPresenter extends MvpBasePresenter<IWordCardTrainingView>
        implements IWordCardTrainingPresenter {

    private Random random = new Random();

    private IRepository repository;
    public WordCardTrainingPresenter(@NonNull IRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initTraining() {
        //wordCardList = repository.getWordCardList(); ///SUB LIST!!!
        //init(wordCards);
    }

    @Override
    public void initRuleTraining(int ruleId) {
        //wordCardList = repository.getWordCardListByRule(ruleId);  ///SUB LIST
        //init(wordCards);
    }

    @Override
    public void initRulePointTraining(int rulePointId) {
        init(repository.getWordCardListByRulePoint(rulePointId));
    }

    private boolean correctWord;

    private int wordModelIndex;
    private List<WordCard> wordCardList;

    private void init(List<WordCard> wordCardList) {
        this.wordCardList = wordCardList;
        this.correctWord = random.nextBoolean();

        WordCard wordCard = wordCardList.get(wordModelIndex);
        if (isViewAttached()) {
            getView().initProgressView(wordCardList.size());

            getView().setScoreView(wordCardList.size(), 0);
            getView().setWordView(correctWord ? wordCard.getCorrectWord() : wordCard.getIncorrectWord());

            getView().setCorrectWordViewVisibility(false);

            getView().setNextButtonVisibility(false);
            getView().setAnswersButtonVisibility(true);
        }
    }

    @Override
    public void onNextClick() {
        wordModelIndex++;
        if (wordModelIndex < wordCardList.size()) {
            correctWord = random.nextBoolean();
            WordCard wordCard = wordCardList.get(wordModelIndex);
            if (isViewAttached()) {
                getView().setNextButtonVisibility(false);
                getView().setAnswersButtonVisibility(true);

                getView().setCorrectWordViewVisibility(false);

                getView().setWordView(correctWord ? wordCard.getCorrectWord() : wordCard.getIncorrectWord());
            }
        } else {
            if (isViewAttached()) {
                getView().setAnswersButtonVisibility(false);
            }
        }
    }

    private int trueAnswerCount;

    @Override
    public void onTrueAnswerClick() {
        updateView(correctWord);
    }

    @Override
    public void onFalseAnswerClick() {
        updateView( ! correctWord);
    }


    private void updateView(boolean trueAnswer) {
        if (trueAnswer) {
            trueAnswerCount++;
            if (isViewAttached()) {
                getView().setScoreView(wordCardList.size(), trueAnswerCount);
            }
        } else {
            WordCard wordCard = wordCardList.get(wordModelIndex);
            if (isViewAttached()) {
                getView().setCorrectWordView(wordCard.getCorrectWord());
                getView().setCorrectWordViewVisibility(true);
            }
        }

        if (isViewAttached()) {
            getView().setNextButtonVisibility(true);
            getView().setAnswersButtonVisibility(false);

            getView().setWordResultView(trueAnswer);
            getView().setProgressViewItem(wordModelIndex, trueAnswer);
        }
    }
}
