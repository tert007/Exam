package com.greenkeycompany.exam.fragment.ruledetail.view;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.greenkeycompany.exam.FragmentListener;
import com.greenkeycompany.exam.R;
import com.greenkeycompany.exam.TrainingType;
import com.greenkeycompany.exam.activity.view.ActionBarView;
import com.greenkeycompany.exam.fragment.ScoreUtil;
import com.greenkeycompany.exam.fragment.ruledetail.presenter.IRuleDetailPresenter;
import com.greenkeycompany.exam.fragment.ruledetail.presenter.RuleDetailPresenter;
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

public class RuleDetailFragment extends MvpFragment<IRuleDetailView, IRuleDetailPresenter>
        implements IRuleDetailView {

    private ActionBarView actionBarView;
    private RealmRepository realmRepository = new RealmRepository(Realm.getDefaultInstance());

    @NonNull
    @Override
    public IRuleDetailPresenter createPresenter() {
        return new RuleDetailPresenter(realmRepository);
    }

    private static final String RULE_ID_PARAM = "rule_id";
    private int ruleId;

    public static RuleDetailFragment newInstance(int ruleId) {
        RuleDetailFragment fragment = new RuleDetailFragment();
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

        actionBarView = (ActionBarView) getActivity();

        return parentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.init(ruleId);
    }

    @Override
    public void requestToSetActionBarTitle(String title) {
        actionBarView.setActionBarTitle(title);
    }

    @Override
    public void setBackgroundColor(@ColorInt int color) {
        parentView.setBackgroundColor(color);
    }

    @BindView(R.id.rule_description_completed_image_view) ImageView ruleDescriptionCompletedImageView;
    @Override
    public void setRuleDescriptionCompleted(boolean completed) {
        ruleDescriptionCompletedImageView.setImageResource(completed ? R.drawable.ic_checked : R.drawable.ic_unchecked);
    }

    @BindView(R.id.rule_training_completed_image_view) ImageView ruleTrainingCompletedImageView;
    @Override
    public void setRuleTrainingCompleted(boolean completed) {
        ruleTrainingCompletedImageView.setImageResource(completed ? R.drawable.ic_checked : R.drawable.ic_unchecked);
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
    public void showTrainingLockedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.training_locked_title);
        builder.setMessage(R.string.training_locked_message);

        builder.setPositiveButton(R.string.locked_close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void showRuleExamLockedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.exam_locked_title);
        builder.setMessage(R.string.exam_locked_message);

        builder.setPositiveButton(R.string.locked_close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @OnClick(R.id.rule_description_view)
    public void onRuleDescriptionViewClick() {
        presenter.onDescriptionViewClick();
    }

    @OnClick(R.id.rule_training_view)
    public void onRuleTrainingViewClick() {
        presenter.onTrainingViewClick();
    }

    @OnClick(R.id.rule_exam_view)
    public void onRuleExamViewClick() {
        presenter.onRuleExamViewClick();
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

    @Override
    public void requestToShowDescription(int ruleId) {
        fragmentListener.requestToSetRuleDescriptionFragment(ruleId);
    }

    @Override
    public void requestToShowTraining(int ruleId) {
        fragmentListener.requestToSetTrainingMenuFragment(ruleId);
    }

    @Override
    public void requestToShowRuleExam(int ruleId) {
        fragmentListener.requestToSetWordCardTrainingFragment(TrainingType.RULE_EXAM, ruleId);
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
