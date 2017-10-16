package com.greenkeycompany.exam.fragment.wordcardresult.presenter;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.fragment.wordcardresult.view.IWordCardRulePointResultView;
import com.greenkeycompany.exam.repository.IRepository;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

/**
 * Created by tert0 on 15.10.2017.
 */

public class WordCardRulePointResultPresenter extends MvpBasePresenter<IWordCardRulePointResultView>
        implements IWordCardRulePointResultPresenter {

    private IRepository repository;
    public WordCardRulePointResultPresenter(@NonNull IRepository repository) {
        this.repository = repository;
    }

    @Override
    public void init(int rulePointId, int wordCardCount, int[] wrongAnswerWordCardIds) {

    }
}
