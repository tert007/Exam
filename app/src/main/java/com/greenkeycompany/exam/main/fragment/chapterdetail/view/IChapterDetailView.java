package com.greenkeycompany.exam.main.fragment.chapterdetail.view;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import com.greenkeycompany.exam.main.fragment.chapterdetail.model.RuleMenuItem;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

/**
 * Created by tert0 on 20.09.2017.
 */

public interface IChapterDetailView extends MvpView {
    void requestToSetActionBarTitle(String title);

    void setChapterScore(float score);
    void setChapterViewColor(@ColorInt int color);
    void setChapterWordCardCompletedCount(int wordCardCompletedCount, int wordCardCount);

    void setRuleItemList(@NonNull List<RuleMenuItem> ruleList);

    void showChapterTrainingLockedDialog();

    void requestToStartChapterTraining(int chapterId);
    void requestToSetRuleDetailFragment(int ruleId);
}
