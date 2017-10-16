package com.greenkeycompany.exam.fragment.chapter.view;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.fragment.chapter.model.RuleMenuItem;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

/**
 * Created by tert0 on 20.09.2017.
 */

public interface IChapterView extends MvpView {
    void setItemList(@NonNull List<RuleMenuItem> ruleList);

    void requestToSetRuleFragment(int ruleId);
}
