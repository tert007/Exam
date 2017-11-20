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
            if (FragmentType.isResultFragmentType(fragmentType)) {
                if (PremiumUtil.isPremiumUser()) {
                    FragmentType trainingFragmentType = fragmentType.getParent();
                    if (isViewAttached()) {
                        getView().backStack(trainingFragmentType); // Снять со стека 2 фрагмента, с результатом и с тренировкой
                    }
                    fragmentType = trainingFragmentType.getParent();
                } else {
                    if (random.nextBoolean()) {
                        if (isViewAttached()) {
                            getView().showInterstitialAd();
                        }
                    } else {
                        FragmentType trainingFragmentType = fragmentType.getParent();
                        if (isViewAttached()) {
                            getView().backStack(trainingFragmentType); // Снять со стека 2 фрагмента, с результатом и с тренировкой
                        }
                        fragmentType = trainingFragmentType.getParent();
                    }
                }
            } else {
                if (isViewAttached()) {
                    getView().backStack();
                }
                fragmentType = fragmentType.getParent();
            }
        }
    }

    @Override
    public void onResultFragmentRestartClick(@NonNull TrainingType trainingType) {
        fragmentType = TrainingType.getReferenceTrainingFragmentType(trainingType);
        if (isViewAttached()) {
            getView().backStack();
        }
    }

    private Random random = new Random();

    @Override
    public void onInterstitialAdConsumed() { //Вызывается после просмотра рекламы.
        FragmentType trainingFragmentType = fragmentType.getParent();
        if (isViewAttached()) {
            getView().backStack(trainingFragmentType); // Снять со стека 2 фрагмента, с результатом и с тренировкой
        }
        fragmentType = trainingFragmentType.getParent();
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
            fragmentType = FragmentType.RULE_DETAIL;
            if (isViewAttached()) {
                getView().setRuleDetailFragment(ruleId);
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
        fragmentType = TrainingType.getReferenceTrainingFragmentType(trainingType);
        if (isViewAttached()) {
            getView().setWordCardTrainingFragment(trainingType, id);
        }
    }

    @Override
    public void requestToSetWordCardResultFragment(@NonNull TrainingType trainingType, int resultId, int[] wrongAnswerWordCardIds) {
        fragmentType = TrainingType.getReferenceResultFragmentType(trainingType);
        if (isViewAttached()) {
            getView().setWordCardResultFragment(trainingType, resultId, wrongAnswerWordCardIds);
        }
    }
}
