package com.greenkeycompany.exam.activity.presenter;

import android.graphics.Color;

import com.greenkeycompany.exam.FragmentType;
import com.greenkeycompany.exam.activity.view.IMainView;
import com.greenkeycompany.exam.fragment.ChapterColorUtil;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

/**
 * Created by tert0 on 04.10.2017.
 */

public class MainPresenter extends MvpBasePresenter<IMainView>
        implements IMainPresenter {

    private FragmentType fragmentType;

    @Override
    public void onBackPressed() {

    }

    @Override
    public void requestToSetMainMenuFragment() {
        fragmentType = FragmentType.MAIN_MENU;
        if (isViewAttached()) {
            getView().setMainFragment();
            //getView().setActionBarColor(Color.WHITE);
            //getView().setActionBarTitleTextColor(Color.BLACK);
        }
    }

    @Override
    public void requestToSetChapterFragment(int chapterId) {
        fragmentType = FragmentType.CHAPTER_MENU;
        if (isViewAttached()) {
            getView().setChapterFragment(chapterId);
            //getView().setActionBarColor(ChapterColorUtil.getReferenceColor(chapterId));
            //getView().setActionBarTitleTextColor(Color.WHITE);
        }
    }

    @Override
    public void requestToSetRuleFragment(int ruleId) {
        fragmentType = FragmentType.RULE_MENU;
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
    public void requestToSetWordCardTrainingFragment() {
        fragmentType = FragmentType.WORD_CARD_RULE_TRAINING;
        if (isViewAttached()) {
            getView().setWordCardTrainingFragment();
        }
    }

    @Override
    public void requestToSetWordCardRuleTrainingFragment(int ruleId) {
        fragmentType = FragmentType.WORD_CARD_RULE_TRAINING;
        if (isViewAttached()) {
            getView().setWordCardRuleTrainingFragment(ruleId);
        }
    }

    @Override
    public void requestToSetWordCardRulePointTrainingFragment(int rulePointId) {
        fragmentType = FragmentType.WORD_CARD_RULE_TRAINING;
        if (isViewAttached()) {
            getView().setWordCardRulePointTrainingFragment(rulePointId);
        }
    }

    @Override
    public void requestToSetRuleResultFragment(int ruleId, int wordCardCount, int[] wrongAnswerWordCardIds) {

    }

    @Override
    public void requestToSetRulePointResultFragment(int rulePointId, int wordCardCount, int[] wrongAnswerWordCardIds) {
        ///
        if (isViewAttached()) {
            getView().setWordCardRulePointResultFragment(rulePointId, wordCardCount, wrongAnswerWordCardIds);
        }
    }
}
