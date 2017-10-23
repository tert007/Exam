package com.greenkeycompany.exam.fragment.wordcardresult.view;

import android.content.Context;
import android.os.Bundle;
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
import com.greenkeycompany.exam.TrainingType;
import com.greenkeycompany.exam.fragment.wordcardresult.fragment.ContentFragment;
import com.greenkeycompany.exam.fragment.wordcardresult.fragment.WordListFragment;
import com.greenkeycompany.exam.fragment.wordcardresult.presenter.IWordCardResultPresenter;
import com.greenkeycompany.exam.fragment.wordcardresult.presenter.WordCardResultPresenter;
import com.greenkeycompany.exam.repository.RealmRepository;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;

/**
 * Created by tert0 on 20.09.2017.
 */

public class WordCardResultFragment extends MvpFragment<IWordCardResultView, IWordCardResultPresenter>
        implements IWordCardResultView {

    private RealmRepository realmRepository = new RealmRepository(Realm.getDefaultInstance());

    @NonNull
    @Override
    public IWordCardResultPresenter createPresenter() {
        return new WordCardResultPresenter(realmRepository);
    }

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

    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.view_pager) ViewPager viewPager;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.word_card_result_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);

        if (wrongAnswerWordCardIds.length == 0) {
            tabLayout.setVisibility(View.GONE);

            viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {

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
            viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {

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

            tabLayout.setupWithViewPager(viewPager);
            tabLayout.getTabAt(0).setText(R.string.word_card_result);
            tabLayout.getTabAt(1).setText(R.string.word_card_list);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.init(trainingType, resultId, wrongAnswerWordCardIds);
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
    public void requestToRefresh(@NonNull TrainingType trainingType, int trainingId) {
        fragmentListener.requestToSetWordCardTrainingFragment(trainingType, trainingId);
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
