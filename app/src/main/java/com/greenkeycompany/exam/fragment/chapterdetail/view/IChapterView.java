package com.greenkeycompany.exam.fragment.chapterdetail.view;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import com.greenkeycompany.exam.fragment.chapterdetail.model.RuleMenuItem;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

/**
 * Created by tert0 on 20.09.2017.
 */

public interface IChapterView extends MvpView {
    void setChapterDescriptionViewColor(@ColorInt int color);
    void setRuleItemList(@NonNull List<RuleMenuItem> ruleList);

    void showChapterTrainingLockedDialog();

    void requestToStartChapterTraining(int chapterId);
    void requestToSetRuleDetailFragment(int ruleId);
}
