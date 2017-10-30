package com.greenkeycompany.exam.activity.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.FragmentType;
import com.greenkeycompany.exam.TrainingType;
import com.greenkeycompany.exam.activity.view.IMainView;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

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
            if (fragmentType == FragmentType.WORD_CARD_RULE_POINT_RESULT ||
                fragmentType == FragmentType.WORD_CARD_RULE_RESULT ||
                fragmentType == FragmentType.WORD_CARD_CHAPTER_RESULT) {
                if (isViewAttached()) {
                    getView().backStack(fragmentType.getParent());
                }
            } else {
                if (isViewAttached()) {
                    getView().backStack();
                }
            }

            fragmentType = fragmentType.getParent();
            if (fragmentType == FragmentType.MAIN) {
                if (isViewAttached()) {
                    getView().setActionBarHomeButtonVisibility(false);
                }
            }
        }
    }

    @Override
    public void requestToSetMainMenuFragment() {
        fragmentType = FragmentType.MAIN;
        if (isViewAttached()) {
            getView().setMainFragment();
            getView().setActionBarHomeButtonVisibility(false);
        }
    }

    @Override
    public void requestToSetChapterFragment(int chapterId) {
        fragmentType = FragmentType.CHAPTER;
        if (isViewAttached()) {
            getView().setChapterFragment(chapterId);
            getView().setActionBarHomeButtonVisibility(true);
        }
    }

    @Override
    public void requestToSetRuleDetailFragment(int ruleId) {
        fragmentType = FragmentType.RULE;
        if (isViewAttached()) {
            getView().setRuleFragment(ruleId);
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
            case RULE_POINT: fragmentType = FragmentType.WORD_CARD_RULE_POINT_TRAINING; break;
            case RULE: fragmentType = FragmentType.WORD_CARD_RULE_TRAINING; break;
            case CHAPTER: fragmentType = FragmentType.WORD_CARD_CHAPTER_TRAINING; break;
        }
        if (isViewAttached()) {
            getView().setWordCardTrainingFragment(trainingType, id);
        }
    }

    @Override
    public void requestToSetWordCardResultFragment(@NonNull TrainingType trainingType, int resultId, int[] wrongAnswerWordCardIds) {
        switch (trainingType) {
            case RULE_POINT: fragmentType = FragmentType.WORD_CARD_RULE_POINT_RESULT; break;
            case RULE: fragmentType = FragmentType.WORD_CARD_RULE_RESULT; break;
            case CHAPTER: fragmentType = FragmentType.WORD_CARD_CHAPTER_RESULT; break;
        }
        if (isViewAttached()) {
            getView().setWordCardResultFragment(trainingType, resultId, wrongAnswerWordCardIds);
        }
    }
}
