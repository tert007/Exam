package com.greenkeycompany.exam.fragment.rule.view;

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
import com.greenkeycompany.exam.fragment.rule.presenter.IRulePresenter;
import com.greenkeycompany.exam.fragment.rule.presenter.RulePresenter;
import com.greenkeycompany.exam.repository.RealmRepository;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;

/**
 * Created by tert0 on 20.09.2017.
 */

public class RuleFragment extends MvpFragment<IRuleView, IRulePresenter>
        implements IRuleView {

    private RealmRepository realmRepository = new RealmRepository(Realm.getDefaultInstance());

    @NonNull
    @Override
    public IRulePresenter createPresenter() {
        return new RulePresenter(realmRepository);
    }

    private static final String RULE_ID_PARAM = "rule_id";
    private int ruleId;

    public static RuleFragment newInstance(int ruleId) {
        RuleFragment fragment = new RuleFragment();
        Bundle args = new Bundle();
        args.putInt(RULE_ID_PARAM, ruleId);
        fragment.setArguments(args);
        return fragment;
    }

    private View parentView;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.rule_detail_fragment, container, false);

        unbinder = ButterKnife.bind(this, parentView);

        return parentView;
    }

    @Override
    public void setBackgroundColor(@ColorInt int color) {
        parentView.setBackgroundColor(color);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ruleId = getArguments().getInt(RULE_ID_PARAM);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.init(ruleId);
    }

    @OnClick(R.id.rule_view)
    public void onRuleViewClick() {
        fragmentListener.requestToSetRuleDescriptionFragment(ruleId);
    }

    @OnClick(R.id.training_view)
    public void onTrainingViewClick() {
        fragmentListener.requestToSetTrainingMenuFragment(ruleId);
    }

    @OnClick(R.id.exam_view)
    public void onExamViewClick() {
        fragmentListener.requestToSetWordCardRuleTrainingFragment(ruleId);
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
