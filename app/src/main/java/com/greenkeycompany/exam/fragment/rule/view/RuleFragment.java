package com.greenkeycompany.exam.fragment.rule.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.greenkeycompany.exam.FragmentListener;
import com.greenkeycompany.exam.R;
import com.greenkeycompany.exam.TrainingType;
import com.greenkeycompany.exam.fragment.ScoreUtil;
import com.greenkeycompany.exam.fragment.rule.presenter.IRulePresenter;
import com.greenkeycompany.exam.fragment.rule.presenter.RulePresenter;
import com.greenkeycompany.exam.repository.RealmRepository;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import butterknife.BindView;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ruleId = getArguments().getInt(RULE_ID_PARAM);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.init(ruleId);
    }

    @Override
    public void setBackgroundColor(@ColorInt int color) {
        parentView.setBackgroundColor(color);
    }

    @OnClick(R.id.rule_description_view)
    public void onRuleDescriptionViewClick() {
        fragmentListener.requestToSetRuleDescriptionFragment(ruleId);
    }

    @BindView(R.id.rule_description_completed_image_view) ImageView ruleDescriptionCompletedImageView;

    @Override
    public void setRuleDescriptionCompleted(boolean completed) {
        ruleDescriptionCompletedImageView.setImageResource(completed ? R.drawable.ic_checked : R.drawable.ic_unchecked);
    }

    @OnClick(R.id.rule_training_view)
    public void onRuleTrainingViewClick() {
        fragmentListener.requestToSetTrainingMenuFragment(ruleId);
    }

    @BindView(R.id.rule_training_completed_image_view) ImageView ruleTrainingCompletedImageView;

    @Override
    public void setRuleTrainingCompleted(boolean completed) {
        ruleTrainingCompletedImageView.setImageResource(completed ? R.drawable.ic_checked : R.drawable.ic_unchecked);
    }

    @OnClick(R.id.rule_exam_view)
    public void onRuleExamViewClick() {
        fragmentListener.requestToSetWordCardTrainingFragment(TrainingType.RULE, ruleId);
    }

    @BindView(R.id.rule_exam_completed_text_view) TextView ruleExamCompletedTextView;

    @Override
    public void setRuleExamUncompleted() {
        ruleExamCompletedTextView.setBackgroundResource(R.drawable.ic_unchecked);
    }

    @Override
    public void setRuleExamCompleted(float score) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.OVAL);
        gradientDrawable.setColor(ScoreUtil.getReferenceColor(score));

        ruleExamCompletedTextView.setBackground(gradientDrawable);
        ruleExamCompletedTextView.setText(String.valueOf(score));
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
