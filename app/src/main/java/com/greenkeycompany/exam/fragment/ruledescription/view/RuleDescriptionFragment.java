package com.greenkeycompany.exam.fragment.ruledescription.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greenkeycompany.exam.R;
import com.greenkeycompany.exam.fragment.ruledescription.presenter.IRuleDescriptionPresenter;
import com.greenkeycompany.exam.fragment.ruledescription.presenter.RuleDescriptionPresenter;
import com.greenkeycompany.exam.repository.RealmRepository;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;

/**
 * Created by tert0 on 04.10.2017.
 */

public class RuleDescriptionFragment extends MvpFragment<IRuleDescriptionView, IRuleDescriptionPresenter>
        implements IRuleDescriptionView {

    private RealmRepository realmRepository = new RealmRepository(Realm.getDefaultInstance());

    @NonNull
    @Override
    public IRuleDescriptionPresenter createPresenter() {
        return new RuleDescriptionPresenter(realmRepository);
    }

    private static final String RULE_ID_PARAM = "rule_id";
    private static final String RULE_POINT_ID_PARAM = "rule_point_id";

    public static final int ALL_RULE_POINTS = 0;

    private int ruleId;
    private int rulePointId;

    public static RuleDescriptionFragment newInstance(int ruleId, int rulePointId) {
        RuleDescriptionFragment fragment = new RuleDescriptionFragment();
        Bundle args = new Bundle();
        args.putInt(RULE_ID_PARAM, ruleId);
        args.putInt(RULE_POINT_ID_PARAM, rulePointId);
        fragment.setArguments(args);
        return fragment;
    }

    public static RuleDescriptionFragment newInstance(int ruleId) {
        return newInstance(ruleId, ALL_RULE_POINTS);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ruleId = getArguments().getInt(RULE_ID_PARAM);
            rulePointId = getArguments().getInt(RULE_POINT_ID_PARAM);
        }
    }

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rule_content_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (ALL_RULE_POINTS == rulePointId) {
            presenter.initRule(ruleId);
        } else {
            presenter.initRulePoint(rulePointId);
        }
    }

    @BindView(R.id.content_holder_layout) LinearLayout contentHolderLayout;
    @Override
    public void addRulePointView(@NonNull String title, @NonNull String message) {
        TextView textView = new TextView(getContext());
        textView.setText(Html.fromHtml(message));
        textView.setTextSize(16);
        contentHolderLayout.addView(textView);
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
}
