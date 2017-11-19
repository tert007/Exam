package com.greenkeycompany.exam.activity.view;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.FragmentListener;
import com.greenkeycompany.exam.FragmentType;
import com.greenkeycompany.exam.TrainingType;
import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by tert0 on 04.10.2017.
 */

public interface ActionBarView {
    void setActionBarTitle(String title);
    void setActionBarHomeButtonVisibility(boolean visible);
    void setActionBarPremiumButtonVisibility(boolean visible);
}
