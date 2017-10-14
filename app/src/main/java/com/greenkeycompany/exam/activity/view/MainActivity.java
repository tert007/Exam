package com.greenkeycompany.exam.activity.view;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.greenkeycompany.exam.R;
import com.greenkeycompany.exam.activity.presenter.IMainPresenter;
import com.greenkeycompany.exam.activity.presenter.MainPresenter;
import com.greenkeycompany.exam.fragment.mainmenu.view.MainMenuFragment;
import com.greenkeycompany.exam.fragment.rule.view.RuleFragment;
import com.greenkeycompany.exam.fragment.ruledescription.view.RuleDescriptionFragment;
import com.greenkeycompany.exam.fragment.chapter.view.ChapterFragment;
import com.greenkeycompany.exam.fragment.trainingmenu.view.TrainingMenuFragment;
import com.greenkeycompany.exam.fragment.wordcard.view.WordCardTrainingFragment;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MvpActivity<IMainView, IMainPresenter>
        implements IMainView {

    @NonNull
    @Override
    public IMainPresenter createPresenter() {
        return new MainPresenter();
    }

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.toolbar_title_text_view) TextView toolbarTitleTextView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        presenter.requestToSetMainMenuFragment();
    }

    /*
    @Override
    public void setActionBarColor(@ColorInt int color) {
        toolbar.setBackgroundColor(color);
    }

    @Override
    public void setActionBarTitleTextColor(@ColorInt int color) {
        toolbarTitleTextView.setTextColor(color);
    }
    */

    @Override
    public void requestToSetMainMenuFragment() {
        presenter.requestToSetMainMenuFragment();
    }

    @Override
    public void requestToSetChapterFragment(int chapterId) {
        presenter.requestToSetChapterFragment(chapterId);
    }

    @Override
    public void requestToSetRuleFragment(int ruleId) {
        presenter.requestToSetRuleFragment(ruleId);
    }

    @Override
    public void requestToSetRuleDescriptionFragment(int ruleId) {
        presenter.requestToSetRuleDescriptionFragment(ruleId);
    }

    @Override
    public void requestToSetTrainingMenuFragment(int ruleId) {
        presenter.requestToSetTrainingMenuFragment(ruleId);
    }

    @Override
    public void requestToSetWordCardTrainingFragment() {
        presenter.requestToSetWordCardTrainingFragment();
    }

    @Override
    public void requestToSetWordCardRuleTrainingFragment(int ruleId) {
        presenter.requestToSetWordCardRuleTrainingFragment(ruleId);
    }

    @Override
    public void requestToSetWordCardRulePointTrainingFragment(int rulePointId) {
        presenter.requestToSetWordCardRulePointTrainingFragment(rulePointId);
    }

    @Override
    public void setMainFragment() {
        setFragment(MainMenuFragment.newInstance());
    }

    @Override
    public void setChapterFragment(int chapterId) {
        setFragment(ChapterFragment.newInstance(chapterId));
    }

    @Override
    public void setRuleFragment(int ruleId) {
        setFragment(RuleFragment.newInstance(ruleId));
    }

    @Override
    public void setRuleDescriptionFragment(int ruleId) {
        setFragment(RuleDescriptionFragment.newInstance(ruleId));
    }

    @Override
    public void setTrainingMenuFragment(int ruleId) {
        setFragment(TrainingMenuFragment.newInstance(ruleId));
    }

    @Override
    public void setWordCardTrainingFragment() {
        setFragment(WordCardTrainingFragment.newTrainingInstance());
    }

    @Override
    public void setWordCardRuleTrainingFragment(int ruleId) {
        setFragment(WordCardTrainingFragment.newRuleTrainingInstance(ruleId));
    }

    @Override
    public void setWordCardRulePointTrainingFragment(int rulePointId) {
        setFragment(WordCardTrainingFragment.newRulePointTrainingInstance(rulePointId));
    }

    public void setFragment(Fragment fragment) {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, fragment).
                //addToBackStack(null).
                commit();
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }
}
