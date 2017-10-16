package com.greenkeycompany.exam.fragment.wordcardresult.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greenkeycompany.exam.FragmentListener;
import com.greenkeycompany.exam.R;
import com.greenkeycompany.exam.fragment.wordcardlist.WordCardListFragment;
import com.greenkeycompany.exam.fragment.wordcardresult.presenter.IWordCardRulePointResultPresenter;
import com.greenkeycompany.exam.fragment.rule.view.IRuleView;
import com.greenkeycompany.exam.fragment.wordcardresult.presenter.WordCardRulePointResultPresenter;
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

public class WordCardRulePointResultFragment extends MvpFragment<IWordCardRulePointResultView, IWordCardRulePointResultPresenter>
        implements IWordCardRulePointResultView {

    private RealmRepository realmRepository = new RealmRepository(Realm.getDefaultInstance());

    @NonNull
    @Override
    public IWordCardRulePointResultPresenter createPresenter() {
        return new WordCardRulePointResultPresenter(realmRepository);
    }

    private static final String RULE_POINT_ID_PARAM = "rule_point_id";
    private static final String WORD_CARD_COUNT_PARAM = "word_card_count";
    private static final String WRONG_WORD_CARD_ID_ARRAY_PARAM = "wrong_answer_word_card_ids";

    private int rulePointId;
    private int wordCardCount;
    private int[] wrongAnswerWordCardIds;

    public static WordCardRulePointResultFragment newInstance(int rulePointId, int wordCardCount, int[] wrongAnswerWordCardIds) {
        WordCardRulePointResultFragment fragment = new WordCardRulePointResultFragment();
        Bundle args = new Bundle();
        args.putInt(RULE_POINT_ID_PARAM, rulePointId);
        args.putInt(WORD_CARD_COUNT_PARAM, wordCardCount);
        args.putIntArray(WRONG_WORD_CARD_ID_ARRAY_PARAM, wrongAnswerWordCardIds);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rulePointId = getArguments().getInt(RULE_POINT_ID_PARAM);
            wordCardCount = getArguments().getInt(WORD_CARD_COUNT_PARAM);
            wrongAnswerWordCardIds = getArguments().getIntArray(WRONG_WORD_CARD_ID_ARRAY_PARAM);
        }
    }

    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.view_pager) ViewPager viewPager;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.word_card_result_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);

        viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {

            private static final int FRAGMENT_COUNT = 2;

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0: return WordCardListFragment.newInstance(wrongAnswerWordCardIds);
                    case 1: return WordCardListFragment.newInstance(wrongAnswerWordCardIds);
                    default: return null;
                }
            }

            @Override
            public int getCount() {
                return FRAGMENT_COUNT;
            }

        });
        tabLayout.setupWithViewPager(viewPager);

        TabLayout.Tab resultTab = tabLayout.getTabAt(0);
        resultTab.setText(R.string.word_card_result);

        TabLayout.Tab listTab  = tabLayout.getTabAt(1);
        listTab.setText(R.string.word_card_list);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
    public void requestToSetWordCardRulePointFragment(int rulePointId) {
        //fragmentListener.requestToSetWordCardRulePointTrainingFragment(rulePointId);
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
