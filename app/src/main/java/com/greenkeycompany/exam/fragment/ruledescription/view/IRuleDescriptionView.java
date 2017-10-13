package com.greenkeycompany.exam.fragment.ruledescription.view;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by tert0 on 05.10.2017.
 */

public interface IRuleDescriptionView extends MvpView {
    void addRulePointView(@NonNull String title, @NonNull String message);
}
