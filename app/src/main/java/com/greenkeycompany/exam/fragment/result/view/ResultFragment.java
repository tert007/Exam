package com.greenkeycompany.exam.fragment.result.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greenkeycompany.exam.FragmentListener;
import com.greenkeycompany.exam.R;
import com.greenkeycompany.exam.fragment.result.presenter.IResultPresenter;
import com.greenkeycompany.exam.fragment.rule.view.IRuleView;
import com.greenkeycompany.exam.repository.RealmRepository;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;

/**
 * Created by tert0 on 20.09.2017.
 */

public class ResultFragment extends MvpFragment<IResultView, IResultPresenter>
        implements IRuleView {

    private RealmRepository realmRepository = new RealmRepository(Realm.getDefaultInstance());

    @NonNull
    @Override
    public IResultPresenter createPresenter() {
        return null;
    }

    private static final String WRONG_WORD_CARD_ID_ARRAY_PARAM = "wrong_answer_word_card_ids";
    private int[] wrongAnswerWordCardIds;

    public static ResultFragment newInstance(int[] wordCardIds) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putIntArray(WRONG_WORD_CARD_ID_ARRAY_PARAM, wordCardIds);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            wrongAnswerWordCardIds = getArguments().getIntArray(WRONG_WORD_CARD_ID_ARRAY_PARAM);
        }
    }



    private View parentView;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.rule_menu_fragment, container, false);

        unbinder = ButterKnife.bind(this, parentView);

        return parentView;
    }

    @Override
    public void setRuleDescriptionCompleted(boolean completed) {

    }

    @Override
    public void setRuleTrainingCompleted(boolean completed) {

    }

    @Override
    public void setRuleExamUncompleted() {

    }

    @Override
    public void setRuleExamCompleted(float score) {

    }

    @Override
    public void setBackgroundColor(@ColorInt int color) {
        parentView.setBackgroundColor(color);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //presenter.init(ruleId);
    }

    @OnClick(R.id.rule_description_view)
    public void onRuleViewClick() {
        //fragmentListener.requestToSetRuleDescriptionFragment(ruleId);
    }

    @OnClick(R.id.rule_training_view)
    public void onTrainingViewClick() {
        //fragmentListener.requestToSetTrainingMenuFragment(ruleId);
    }

    @OnClick(R.id.rule_exam_view)
    public void onExamViewClick() {
        //fragmentListener.requestToSetWordCardRuleTrainingFragment(ruleId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realmRepository.close();
    }

    private FragmentListener fragmentListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentListener) {
            fragmentListener = (FragmentListener) context;
        } else {
            throw new RuntimeException("Activity must implement FragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentListener = null;
    }
}
