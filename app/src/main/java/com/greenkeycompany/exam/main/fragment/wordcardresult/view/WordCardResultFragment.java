package com.greenkeycompany.exam.main.fragment.wordcardresult.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.greenkeycompany.exam.main.activity.view.FragmentListener;
import com.greenkeycompany.exam.R;
import com.greenkeycompany.exam.main.TrainingType;
import com.greenkeycompany.exam.main.activity.view.ActionBarView;
import com.greenkeycompany.exam.main.fragment.wordcardresult.fragment.ContentFragment;
import com.greenkeycompany.exam.main.fragment.wordcardresult.fragment.WordListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by tert0 on 20.09.2017.
 */

public class WordCardResultFragment extends Fragment {

    private static final String TRAINING_TYPE_PARAM = "training_type";
    private static final String RESULT_ID_PARAM = "result_id";
    private static final String WRONG_WORD_CARD_ID_ARRAY_PARAM = "wrong_answer_word_card_ids";

    private TrainingType trainingType;
    private int resultId;
    private int[] wrongAnswerWordCardIds;

    public static WordCardResultFragment newInstance(@NonNull TrainingType trainingType,
                                                      int resultId, int[] wrongAnswerWordCardIds) {

        WordCardResultFragment fragment = new WordCardResultFragment();
        Bundle args = new Bundle();
        args.putSerializable(TRAINING_TYPE_PARAM, trainingType);
        args.putInt(RESULT_ID_PARAM, resultId);
        args.putIntArray(WRONG_WORD_CARD_ID_ARRAY_PARAM, wrongAnswerWordCardIds);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            trainingType = (TrainingType) getArguments().getSerializable(TRAINING_TYPE_PARAM);

            resultId = getArguments().getInt(RESULT_ID_PARAM);
            wrongAnswerWordCardIds = getArguments().getIntArray(WRONG_WORD_CARD_ID_ARRAY_PARAM);
        }
    }

    private ActionBarView actionBarView;

    @BindView(R.id.bottom_navigation_view) BottomNavigationView bottomNavigationView;
    @BindView(R.id.view_pager) ViewPager viewPager;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.word_card_result_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);

        actionBarView = (ActionBarView) getActivity();
        actionBarView.setActionBarPremiumButtonVisibility(false);

        if (wrongAnswerWordCardIds.length == 0) {
            bottomNavigationView.setVisibility(View.GONE);

            viewPager.setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {

                private static final int FRAGMENT_COUNT = 1;

                @Override
                public Fragment getItem(int position) {
                    switch (position) {
                        case 0: return ContentFragment.newInstance(trainingType, resultId);
                    }
                    return null;
                }

                @Override
                public int getCount() {
                    return FRAGMENT_COUNT;
                }
            });
        } else {
            viewPager.setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {

                private static final int FRAGMENT_COUNT = 2;

                @Override
                public Fragment getItem(int position) {
                    switch (position) {
                        case 0: return ContentFragment.newInstance(trainingType, resultId);
                        case 1: return WordListFragment.newInstance(wrongAnswerWordCardIds);
                    }
                    return null;
                }

                @Override
                public int getCount() {
                    return FRAGMENT_COUNT;
                }
            });
            viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    switch (position) {
                        case 0: bottomNavigationView.setSelectedItemId(R.id.result); break;
                        case 1: bottomNavigationView.setSelectedItemId(R.id.list); break;
                    }
                }
            });

            bottomNavigationView.setItemIconTintList(null);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.result:
                            viewPager.setCurrentItem(0);
                            return true;
                        case R.id.list:
                            viewPager.setCurrentItem(1);
                            return true;
                    }
                    return true;
                }
            });
        }

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.training_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.restart:
                //presenter.initTraining(trainingType, id);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
