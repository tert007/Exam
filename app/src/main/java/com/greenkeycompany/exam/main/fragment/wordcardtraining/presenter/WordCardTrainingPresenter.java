package com.greenkeycompany.exam.main.fragment.wordcardtraining.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.main.TrainingType;
import com.greenkeycompany.exam.main.util.ScoreUtil;
import com.greenkeycompany.exam.main.util.WordCardListUtil;
import com.greenkeycompany.exam.main.util.TrainingCompletedUtil;
import com.greenkeycompany.exam.main.fragment.wordcardtraining.view.IWordCardTrainingView;
import com.greenkeycompany.exam.main.repository.IRepository;
import com.greenkeycompany.exam.main.repository.model.WordCard;
import com.greenkeycompany.exam.main.repository.model.status.WordCardSetStatus;
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
            case WORD_CARD_SET_TRAINING:
                init(WordCardListUtil.getWordCardSetList(repository.getWordCardListByWordCardSet(trainingId), repository.getWordCardSet(trainingId).getSize()));
                break;
            case RULE_EXAM:
                init(WordCardListUtil.getRuleExamList(repository.getWordCardListByRule(trainingId)));
                break;
            case CHAPTER_EXAM:
                init(WordCardListUtil.getChapterExamList(repository.getWordCardListByChapter(trainingId)));
                break;
            case FINAL_EXAM:
                init(WordCardListUtil.getFinalExamList(repository.getWordCardList()));
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
            getView().setScoreViewVisibility(trainingType != TrainingType.WORD_CARD_SET_TRAINING);

            getView().setWordView(isCorrectWord ? wordCard.getCorrectWord() : wordCard.getIncorrectWord());
            getView().setCorrectWordViewVisibility(false);
            getView().setIncorrectWordViewVisibility(false);

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
                case WORD_CARD_SET_TRAINING: {
                    WordCardSetStatus status = repository.getWordCardSetStatus(trainingId);
                    if (status == null || ! status.isCompleted()) {
                        if (TrainingCompletedUtil.isCompleted(trueAnswerCount, wordCardCount)) {
                            repository.addOrUpdateWordCardSetStatus(trainingId, true);
                        }
                    }
                    repository.addWordCardSetResult(trainingId, trueAnswerCount, System.currentTimeMillis(), new IRepository.Listener() {
                        @Override
                        public void onAdded(int id) {
                            if (isViewAttached()) {
                                getView().requestToSetResultFragment(trainingType, id, getWrongAnswerWordCardIds());
                            }
                        }
                    });
                }
                break;
                case RULE_EXAM: {
                    float score = ScoreUtil.getScore(trueAnswerCount, wordCardCount);
                    repository.addRuleExamResult(trainingId, score, System.currentTimeMillis(), new IRepository.Listener() {
                        @Override
                        public void onAdded(int id) {
                            if (isViewAttached()) {
                                getView().requestToSetResultFragment(trainingType, id, getWrongAnswerWordCardIds());
                            }
                        }
                    });
                }
                break;
                case CHAPTER_EXAM: {
                    float score = ScoreUtil.getScore(trueAnswerCount, wordCardCount);
                    repository.addChapterExamResult(trainingId, score, System.currentTimeMillis(), new IRepository.Listener() {
                        @Override
                        public void onAdded(int id) {
                            if (isViewAttached()) {
                                getView().requestToSetResultFragment(trainingType, id, getWrongAnswerWordCardIds());
                            }
                        }
                    });
                }
                break;
                case FINAL_EXAM:
                    float score = ScoreUtil.getScore(trueAnswerCount, wordCardCount);
                    repository.addFinalExamResult(score, System.currentTimeMillis(), new IRepository.Listener() {
                        @Override
                        public void onAdded(int id) {
                            if (isViewAttached()) {
                                getView().requestToSetResultFragment(trainingType, id, getWrongAnswerWordCardIds());
                            }
                        }
                    });
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
