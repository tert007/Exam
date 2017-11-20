package com.greenkeycompany.exam.main.activity.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.app.PremiumUtil;
import com.greenkeycompany.exam.main.FragmentType;
import com.greenkeycompany.exam.main.TrainingType;
import com.greenkeycompany.exam.main.activity.view.IMainView;
import com.greenkeycompany.exam.main.util.ChapterUtil;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.Random;

/**
 * Created by tert0 on 04.10.2017.
 */

public class MainPresenter extends MvpBasePresenter<IMainView>
        implements IMainPresenter {

    private FragmentType fragmentType;

    @Override
    public void onBackPressed() {
        if (fragmentType.getParent() == null) {
            if (isViewAttached()) {
                getView().finish();
            }
        } else {
            if (fragmentType == FragmentType.WORD_CARD_SET_TRAINING_RESULT ||
                fragmentType == FragmentType.RULE_EXAM_RESULT ||
                fragmentType == FragmentType.CHAPTER_EXAM_RESULT ||
                fragmentType == FragmentType.FINAL_EXAM_RESULT) {
                if (isViewAttached()) {
                    getView().backStack(fragmentType.getParent());
                }
            } else {
                if (isViewAttached()) {
                    getView().backStack();
                }
            }

            fragmentType = fragmentType.getParent();
        }
    }

    @Override
    public void onResultFragmentRestartClick(@NonNull TrainingType trainingType) {
        switch (trainingType) {
            case FINAL_EXAM:
                fragmentType = FragmentType.FINAL_EXAM;
                break;
            case CHAPTER_EXAM:
                fragmentType = FragmentType.CHAPTER_EXAM;
                break;
            case RULE_EXAM:
                fragmentType = FragmentType.RULE_EXAM;
                break;
            case WORD_CARD_SET_TRAINING:
                fragmentType = FragmentType.WORD_CARD_SET_TRAINING;
                break;
        }
        if (isViewAttached()) {
            getView().backStack();
        }
    }

    private int ruleId;
    private Random random = new Random();

    @Override
    public void onInterstitialAdConsumed() {
        fragmentType = FragmentType.RULE_DETAIL;
        if (isViewAttached()) {
            getView().setRuleDetailFragment(ruleId);
        }
    }

    @Override
    public void requestToSetMainMenuFragment() {
        fragmentType = FragmentType.MAIN;
        if (isViewAttached()) {
            getView().setMainFragment();
        }
    }

    @Override
    public void requestToSetChapterDetailFragment(int chapterId) {
        if (PremiumUtil.isPremiumUser()) {
            fragmentType = FragmentType.CHAPTER;
            if (isViewAttached()) {
                getView().setChapterDetailFragment(chapterId);
            }
        } else {
            if (ChapterUtil.isPremiumChapter(chapterId)) {
                if (isViewAttached()) {
                    getView().showPurchasePremiumActivity();
                }
            } else {
                fragmentType = FragmentType.CHAPTER;
                if (isViewAttached()) {
                    getView().setChapterDetailFragment(chapterId);
                }
            }
        }
    }

    @Override
    public void requestToSetRuleDetailFragment(int ruleId) {
        if (PremiumUtil.isPremiumUser()) {
            fragmentType = FragmentType.RULE_DETAIL;
            if (isViewAttached()) {
                getView().setRuleDetailFragment(ruleId);
            }
        } else {
            this.ruleId = ruleId;
            if (random.nextBoolean()) {
                if (isViewAttached()) {
                    getView().showInterstitialAd();
                }
            } else {
                fragmentType = FragmentType.RULE_DETAIL;
                if (isViewAttached()) {
                    getView().setRuleDetailFragment(ruleId);
                }
            }
        }
    }

    @Override
    public void requestToSetRuleDescriptionFragment(int ruleId) {
        fragmentType = FragmentType.RULE_DESCRIPTION;
        if (isViewAttached()) {
            getView().setRuleDescriptionFragment(ruleId);
        }
    }

    @Override
    public void requestToSetTrainingMenuFragment(int ruleId) {
        fragmentType = FragmentType.TRAINING_MENU;
        if (isViewAttached()) {
            getView().setTrainingMenuFragment(ruleId);
        }
    }

    @Override
    public void requestToSetWordCardTrainingFragment(@NonNull TrainingType trainingType, int id) {
        switch (trainingType) {
            case WORD_CARD_SET_TRAINING:
                fragmentType = FragmentType.WORD_CARD_SET_TRAINING;
                break;
            case RULE_EXAM:
                fragmentType = FragmentType.RULE_EXAM;
                break;
            case CHAPTER_EXAM:
                fragmentType = FragmentType.CHAPTER_EXAM;
                break;
            case FINAL_EXAM:
                fragmentType = FragmentType.FINAL_EXAM;
                break;
        }
        if (isViewAttached()) {
            getView().setWordCardTrainingFragment(trainingType, id);
        }
    }

    @Override
    public void requestToSetWordCardResultFragment(@NonNull TrainingType trainingType, int resultId, int[] wrongAnswerWordCardIds) {
        switch (trainingType) {
            case WORD_CARD_SET_TRAINING:
                fragmentType = FragmentType.WORD_CARD_SET_TRAINING_RESULT;
                break;
            case RULE_EXAM:
                fragmentType = FragmentType.RULE_EXAM_RESULT;
                break;
            case CHAPTER_EXAM:
                fragmentType = FragmentType.CHAPTER_EXAM_RESULT;
                break;
            case FINAL_EXAM:
                fragmentType = FragmentType.FINAL_EXAM_RESULT;
                break;
        }
        if (isViewAttached()) {
            getView().setWordCardResultFragment(trainingType, resultId, wrongAnswerWordCardIds);
        }
    }
}
