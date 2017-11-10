package com.greenkeycompany.exam.activity.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.greenkeycompany.exam.FragmentType;
import com.greenkeycompany.exam.R;
import com.greenkeycompany.exam.TrainingType;
import com.greenkeycompany.exam.activity.presenter.IMainPresenter;
import com.greenkeycompany.exam.activity.presenter.MainPresenter;
import com.greenkeycompany.exam.fragment.mainmenu.view.MainMenuFragment;
import com.greenkeycompany.exam.fragment.ruledetail.view.RuleDetailFragment;
import com.greenkeycompany.exam.fragment.ruledescription.view.RuleDescriptionFragment;
import com.greenkeycompany.exam.fragment.chapterdetail.view.ChapterDetailFragment;
import com.greenkeycompany.exam.fragment.trainingmenu.view.TrainingMenuFragment;
import com.greenkeycompany.exam.fragment.wordcardtraining.view.WordCardTrainingFragment;
import com.greenkeycompany.exam.fragment.wordcardresult.view.WordCardResultFragment;
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

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_back_button_icon);

        presenter.requestToSetMainMenuFragment();
    }

    @Override
    public void setActionBarHomeButtonVisibility(boolean visible) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(visible);
    }

    @Override
    public void requestToSetMainMenuFragment() {
        presenter.requestToSetMainMenuFragment();
    }

    @Override
    public void requestToSetChapterFragment(int chapterId) {
        presenter.requestToSetChapterFragment(chapterId);
    }

    @Override
    public void requestToSetRuleDetailFragment(int ruleId) {
        presenter.requestToSetRuleDetailFragment(ruleId);
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
    public void requestToSetWordCardTrainingFragment(@NonNull TrainingType trainingType, int id) {
        presenter.requestToSetWordCardTrainingFragment(trainingType, id);
    }

    @Override
    public void requestToSetWordCardResultFragment(@NonNull TrainingType trainingType, int resultId, int[] wrongAnswerWordCardIds) {
        presenter.requestToSetWordCardResultFragment(trainingType, resultId, wrongAnswerWordCardIds);
    }

    @Override
    public void setMainFragment() {
        setFragment(MainMenuFragment.newInstance(), FragmentType.MAIN);
    }

    @Override
    public void setChapterFragment(int chapterId) {
        setFragment(ChapterDetailFragment.newInstance(chapterId), FragmentType.CHAPTER);
    }

    @Override
    public void setRuleFragment(int ruleId) {
        setFragment(RuleDetailFragment.newInstance(ruleId), FragmentType.RULE);
    }

    @Override
    public void setRuleDescriptionFragment(int ruleId) {
        setFragment(RuleDescriptionFragment.newInstance(ruleId), FragmentType.RULE_DESCRIPTION);
    }

    @Override
    public void setTrainingMenuFragment(int ruleId) {
        setFragment(TrainingMenuFragment.newInstance(ruleId), FragmentType.TRAINING_MENU);
    }

    @Override
    public void setWordCardTrainingFragment(@NonNull TrainingType trainingType, int id) {
        setFragment(WordCardTrainingFragment.newInstance(trainingType, id));
    }

    @Override
    public void setWordCardResultFragment(@NonNull TrainingType trainingType, int resultId, int[] wrongAnswerWordCardIds) {
        setFragment(WordCardResultFragment.newInstance(trainingType, resultId, wrongAnswerWordCardIds));
    }

    public void setFragment(Fragment fragment, FragmentType fragmentType) {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, fragment).
                addToBackStack(fragmentType.name()).
                commit();
    }

    public void setFragment(Fragment fragment) {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, fragment).
                addToBackStack(null).
                commit();
    }

    @Override
    public void backStack() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void backStack(FragmentType fragmentType) {
        getSupportFragmentManager().popBackStack(fragmentType.name(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                presenter.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }
}
