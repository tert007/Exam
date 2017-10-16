package com.greenkeycompany.exam.fragment.wordcard.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.fragment.ScoreUtil;
import com.greenkeycompany.exam.fragment.TrainingCompletedUtil;
import com.greenkeycompany.exam.fragment.wordcard.WordCardTrainingType;
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

    @Override
    public void initTraining() {
        trainingType = WordCardTrainingType.EXAM;

        //wordCardList = repository.getWordCardList(); ///SUB LIST!!!
        //init(wordCards);
    }

    private WordCardTrainingType trainingType;

    private int ruleId;
    private int rulePointId;
    private int wordCardCount;

    @Override
    public void initRuleTraining(int ruleId) {
        this.ruleId = ruleId;
        this.trainingType = WordCardTrainingType.RULE_EXAM;
        //wordCardList = repository.getWordCardListByRule(ruleId);  ///SUB LIST
        //init(wordCards);
    }

    @Override
    public void initRulePointTraining(int rulePointId) {
        this.rulePointId = rulePointId;
        this.trainingType = WordCardTrainingType.RULE_POINT_TRAINING;

        init(repository.getWordCardListByRulePoint(rulePointId));
    }

    private boolean correctWord;

    private int wordModelIndex;
    private List<WordCard> wordCardList;
    private List<WordCard> wrongAnswerWordCardList;

    private void init(@NonNull List<WordCard> wordCardList) {
        this.wordCardList = wordCardList;
        this.wordCardCount = wordCardList.size();
        this.wrongAnswerWordCardList = new ArrayList<>(wordCardCount);

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
        if (wordModelIndex < wordCardCount) {
            correctWord = random.nextBoolean();
            WordCard wordCard = wordCardList.get(wordModelIndex);
            if (isViewAttached()) {
                getView().setNextButtonVisibility(false);
                getView().setAnswersButtonVisibility(true);

                getView().setCorrectWordViewVisibility(false);

                getView().setWordView(correctWord ? wordCard.getCorrectWord() : wordCard.getIncorrectWord());
            }
        } else {
            switch (trainingType) {
                case EXAM:

                    break;
                case RULE_EXAM:
                    repository.addRuleResult(ruleId, ScoreUtil.getScore(wordCardCount, trueAnswerCount), System.currentTimeMillis());
                    if (isViewAttached()) {
                        getView().requestToSetRuleResultFragment(ruleId, wordCardCount, getWrongAnswerWordCardIds());
                    }
                    break;
                case RULE_POINT_TRAINING:
                    RulePoint rulePoint = repository.getRulePoint(rulePointId);
                    if (rulePoint.getWordCardCompletedCount() < trueAnswerCount) {
                        repository.updateRulePoint(rulePointId, trueAnswerCount, TrainingCompletedUtil.isCompleted(trueAnswerCount, wordCardCount));
                    }
                    if (isViewAttached()) {
                        getView().requestToSetRulePointResultFragment(rulePointId, wordCardCount, getWrongAnswerWordCardIds());
                    }
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
                getView().setScoreView(wordCardCount, trueAnswerCount);
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
            getView().setNextButtonVisibility(true);
            getView().setAnswersButtonVisibility(false);

            getView().setWordResultView(trueAnswer);
            getView().setProgressViewItem(wordModelIndex, trueAnswer);
        }
    }
}
