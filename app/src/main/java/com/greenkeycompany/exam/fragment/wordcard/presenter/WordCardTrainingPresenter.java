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
    private int trainingId;

    @Override
    public void initTraining(@NonNull TrainingType trainingType, int trainingId) {
        this.trainingType = trainingType;
        this.trainingId = trainingId;
        switch (trainingType) {
            case RULE_POINT:
                init(WordCardListUtil.getRulePointList(repository.getWordCardListByRulePoint(trainingId), repository.getRulePoint(trainingId).getWordCardTrainingCount()));
                break;
            case RULE:
                init(WordCardListUtil.getRuleList(repository.getWordCardListByRule(trainingId)));
                break;
            case CHAPTER:
                init(WordCardListUtil.getChapterList(repository.getWordCardListByChapter(trainingId)));
                break;
        }
    }

    private int wordCardCount;
    private int wordModelIndex;
    private List<WordCard> wordCardList;
    private List<WordCard> wrongAnswerWordCardList;

    private boolean isCorrectWord;

    private void init(@NonNull List<WordCard> wordCardList) {
        this.wordModelIndex = 0;
        this.trueAnswerCount = 0;

        this.wordCardList = wordCardList;
        this.wordCardCount = wordCardList.size();
        this.wrongAnswerWordCardList = new ArrayList<>(wordCardCount);

        this.isCorrectWord = random.nextBoolean();

        WordCard wordCard = wordCardList.get(wordModelIndex);
        if (isViewAttached()) {
            getView().initProgressView(wordCardList.size());

            getView().setScoreView(0, wordCardList.size());
            getView().setScoreViewVisibility(trainingType != TrainingType.RULE_POINT);

            getView().setWordView(isCorrectWord ? wordCard.getCorrectWord() : wordCard.getIncorrectWord());
            getView().setCorrectWordViewVisibility(false);

            getView().setNextViewVisibility(false);
            getView().setAnswersButtonVisibility(true);
        }
    }

    @Override
    public void onNextClick() {
        wordModelIndex++;
        if (wordModelIndex < wordCardCount) {
            isCorrectWord = random.nextBoolean();
            WordCard wordCard = wordCardList.get(wordModelIndex);
            if (isViewAttached()) {
                getView().setNextViewVisibility(false);
                getView().setAnswersButtonVisibility(true);

                getView().setCorrectWordViewVisibility(false);
                getView().setIncorrectWordViewVisibility(false);

                getView().setWordView(isCorrectWord ? wordCard.getCorrectWord() : wordCard.getIncorrectWord());
            }
        } else {
            switch (trainingType) {
                case RULE_POINT: {
                    RulePoint rulePoint = repository.getRulePoint(trainingId);
                    if ( ! rulePoint.isTrainingCompleted()) {
                        if (TrainingCompletedUtil.isCompleted(trueAnswerCount, wordCardCount)) {
                            repository.updateRulePoint(trainingId, true);
                        }
                    }
                    repository.addRulePointResult(trainingId, trueAnswerCount, System.currentTimeMillis(), new IRepository.Listener() {
                        @Override
                        public void onAdded(int id) {
                            if (isViewAttached()) {
                                getView().requestToSetResultFragment(trainingType, id, getWrongAnswerWordCardIds());
                            }
                        }
                    });
                }
                break;
                case RULE: {
                    float score = ScoreUtil.getScore(trueAnswerCount, wordCardCount);
                    repository.addRuleResult(trainingId, score, System.currentTimeMillis(), new IRepository.Listener() {
                        @Override
                        public void onAdded(int id) {
                            if (isViewAttached()) {
                                getView().requestToSetResultFragment(trainingType, id, getWrongAnswerWordCardIds());
                            }
                        }
                    });
                }
                break;
                case CHAPTER: {
                    float score = ScoreUtil.getScore(trueAnswerCount, wordCardCount);
                    repository.addChapterResult(trainingId, score, System.currentTimeMillis(), new IRepository.Listener() {
                        @Override
                        public void onAdded(int id) {
                            if (isViewAttached()) {
                                getView().requestToSetResultFragment(trainingType, id, getWrongAnswerWordCardIds());
                            }
                        }
                    });
                }
                break;
                case FINAL:
                    //
                    //if (isViewAttached()) {
                    //    getView().requestToSetResultFragment(trainingType, trainingId, wordCardCount, getWrongAnswerWordCardIds());
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
        updateView(isCorrectWord);
    }

    @Override
    public void onFalseAnswerClick() {
        updateView( !isCorrectWord);
    }


    private void updateView(boolean trueAnswer) {
        if (trueAnswer) {
            trueAnswerCount++;
            if (isViewAttached()) {
                getView().setScoreView(trueAnswerCount, wordCardCount);
            }
        } else {
            WordCard wordCard = wordCardList.get(wordModelIndex);
            if (isCorrectWord) {
                if (isViewAttached()) {
                    getView().setCorrectWordViewVisibility(true);
                }
            } else {
                if (isViewAttached()) {
                    getView().setIncorrectWordView(wordCard.getCorrectWord());
                    getView().setIncorrectWordViewVisibility(true);
                }
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
