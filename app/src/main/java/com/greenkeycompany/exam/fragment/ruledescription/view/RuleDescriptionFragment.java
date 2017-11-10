package com.greenkeycompany.exam.fragment.ruledescription.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
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
import butterknife.OnClick;
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

    private int ruleId;

    public static RuleDescriptionFragment newInstance(int ruleId) {
        RuleDescriptionFragment fragment = new RuleDescriptionFragment();
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
        presenter.init(ruleId);
    }

    @BindView(R.id.title_text_view) TextView titleTextView;
    @Override
    public void setRuleTitleView(@NonNull String title) {
        titleTextView.setText(title);
    }

    @BindView(R.id.content_holder_layout) LinearLayout contentHolderLayout;
    @Override
    public void addRulePointView(@NonNull String title, @NonNull String message) {
        TextView textView = new TextView(getContext());
        Spanned text;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            text = Html.fromHtml(message, Html.FROM_HTML_MODE_LEGACY);
            textView.setText(text);
        } else {
            text = Html.fromHtml(message);

            textView.setText(text);
        }

        contentHolderLayout.addView(textView);
    }

    @BindView(R.id.completed_view) View completedView;
    @Override
    public void setCompletedButtonVisibility(boolean visible) {
        completedView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.completed_view)
    public void onCompletedViewClick() {
        presenter.onCompletedButtonClick();
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
