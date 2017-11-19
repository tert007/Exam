package com.greenkeycompany.exam.main.fragment.ruledescription.view;

import android.support.annotation.NonNull;
import android.text.Spanned;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by tert0 on 05.10.2017.
 */

public interface IRuleDescriptionView extends MvpView {
    void setRuleTitleView(@NonNull String title);
    void addRulePointView(@NonNull String title, @NonNull String message);

    void setCompletedButtonVisibility(boolean visible);
}
