package com.greenkeycompany.exam.fragment.wordcard.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.TrainingType;
import com.greenkeycompany.exam.fragment.ScoreUtil;
import com.greenkeycompany.exam.fragment.WordCardListUtil;
import com.greenkeycompany.exam.fragment.TrainingCompletedUtil;
import com.greenkeycompany.exam.repository.IRepository;
import com.greenkeycompany.exam.repository.model.RulePoint;
import com.greenkeycompany.exam.repository.model.WordCard;
import com.greenkeycompany.exam.fragment.wordcard.view.IWordCardTrainingView;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.ArrayList;
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

    private TrainingType trainingType;
    private int id;

    @Override
    public void initTraining(@NonNull TrainingType trainingType, int id) {
        this.trainingType = trainingType;
        this.id = id;
        switch (trainingType) {
            case RULE_POINT:
                init(repository.getWordCardListByRulePoint(id));
                break;
            case RULE:
                init(WordCardListUtil.getRuleShuffleSubList(repository.getWordCardListByRule(id)));
                break;
            case CHAPTER:
                init(WordCardListUtil.getChapterShuffleSubList(repository.getWordCardListByChapter(id)));
                break;
        }
    }

    private int wordCardCount;
    private int wordModelIndex;
    private List<WordCard> wordCardList;
    private List<WordCard> wrongAnswerWordCardList;

    private boolean correctWord;

    private void init(@NonNull List<WordCard> wordCardList) {
        this.wordCardList = wordCardList;
        this.wordCardCount = wordCardList.size();
        this.wrongAnswerWordCardList = new ArrayList<>(wordCardCount);

        this.correctWord = random.nextBoolean();

        WordCard wordCard = wordCardList.get(wordModelIndex);
        if (isViewAttached()) {
            getView().initProgressView(wordCardList.size());

            getView().setScoreView(0, wordCardList.size());
            getView().setWordView(correctWord ? wordCard.getCorrectWord() : wordCard.getIncorrectWord());

            getView().setCorrectWordViewVisibility(false);

            getView().setNextViewVisibility(false);
            getView().setAnswersButtonVisibility(true);
        }
    }

    @Override
    public void onNextClick() {
        wordModelIndex++;
        if (wordModelIndex < wordCardCount) {
            correctWord = random.nextBoolean();
            WordCard wordCard = wordCardList.get(wordModelIndex);
            if (isViewAttached()) {
                getView().setNextViewVisibility(false);
                getView().setAnswersButtonVisibility(true);

                getView().setCorrectWordViewVisibility(false);

                getView().setWordView(correctWord ? wordCard.getCorrectWord() : wordCard.getIncorrectWord());
            }
        } else {
            switch (trainingType) {
                case RULE_POINT:
                    RulePoint rulePoint = repository.getRulePoint(id);
                    if (rulePoint.getWordCardCompletedCount() < trueAnswerCount) {
                        repository.updateRulePoint(id, trueAnswerCount, TrainingCompletedUtil.isCompleted(trueAnswerCount, wordCardCount));
                    }

                    if (isViewAttached()) {
                        getView().requestToSetResultFragment(trainingType, id, wordCardCount, getWrongAnswerWordCardIds());
                    }
                    break;
                case RULE:
                    repository.addRuleResult(id, ScoreUtil.getScore(trueAnswerCount, wordCardCount), System.currentTimeMillis());
                    if (isViewAttached()) {
                        getView().requestToSetResultFragment(trainingType, id, wordCardCount, getWrongAnswerWordCardIds());
                    }
                    break;
                case CHAPTER:
                    repository.addChapterResult(id, ScoreUtil.getScore(trueAnswerCount, wordCardCount), System.currentTimeMillis());
                    if (isViewAttached()) {
                        getView().requestToSetResultFragment(trainingType, id, wordCardCount, getWrongAnswerWordCardIds());
                    }
                    break;
                case FINAL:
                    //
                    //if (isViewAttached()) {
                    //    getView().requestToSetResultFragment(trainingType, id, wordCardCount, getWrongAnswerWordCardIds());
                    //}
                    break;
            }
        }
    }

    private int[] getWrongAnswerWordCardIds() {
        int[] wrongAnswerWordCardIds = new int[wrongAnswerWordCardList.size()];
        for (int i = 0; i < wrongAnswerWordCardList.size(); i++) {
            wrongAnswerWordCardIds[i] = wrongAnswerWordCardList.get(i).getId();
        }
        return wrongAnswerWordCardIds;
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
                getView().setScoreView(trueAnswerCount, wordCardCount);
            }
        } else {
            WordCard wordCard = wordCardList.get(wordModelIndex);
            if (isViewAttached()) {
                getView().setCorrectWordView(wordCard.getCorrectWord());
                getView().setCorrectWordViewVisibility(true);
            }
            wrongAnswerWordCardList.add(wordCard);
        }

        if (isViewAttached()) {
            getView().setNextViewVisibility(true);
            getView().setAnswersButtonVisibility(false);

            getView().setWordResultView(trueAnswer);
            getView().setProgressViewItem(wordModelIndex, trueAnswer);
        }
    }
}
