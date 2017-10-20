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
import com.greenkeycompany.exam.repository.model.WordCard;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.List;

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
    private static final String ID_PARAM = "id";
    private static final String WORD_CARD_COUNT_PARAM = "word_card_count";
    private static final String WRONG_WORD_CARD_ID_ARRAY_PARAM = "wrong_answer_word_card_ids";

    private TrainingType trainingType;
    private int id;
    private int wordCardCount;
    private int[] wrongAnswerWordCardIds;


    public static WordCardResultFragment newFinalTestInstance(int wordCardCount, int[] wrongAnswerWordCardIds) {
        return newInstance(TrainingType.FINAL, 0, wordCardCount, wrongAnswerWordCardIds);
    }

    public static WordCardResultFragment newChapterTestInstance(int chapterId, int wordCardCount, int[] wrongAnswerWordCardIds) {
        return newInstance(TrainingType.CHAPTER, chapterId, wordCardCount, wrongAnswerWordCardIds);
    }

    public static WordCardResultFragment newRuleTestInstance(int ruleId, int wordCardCount, int[] wrongAnswerWordCardIds) {
        return newInstance(TrainingType.RULE, ruleId, wordCardCount, wrongAnswerWordCardIds);
    }

    public static WordCardResultFragment newRulePointInstance(int rulePointId, int wordCardCount, int[] wrongAnswerWordCardIds) {
        return newInstance(TrainingType.RULE_POINT, rulePointId, wordCardCount, wrongAnswerWordCardIds);
    }

    private static WordCardResultFragment newInstance(@NonNull TrainingType trainingType,
                                                      int id,
                                                      int wordCardCount,
                                                      int[] wrongAnswerWordCardIds) {

        WordCardResultFragment fragment = new WordCardResultFragment();
        Bundle args = new Bundle();
        args.putSerializable(TRAINING_TYPE_PARAM, trainingType);
        args.putInt(ID_PARAM, id);
        args.putInt(WORD_CARD_COUNT_PARAM, wordCardCount);
        args.putIntArray(WRONG_WORD_CARD_ID_ARRAY_PARAM, wrongAnswerWordCardIds);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            trainingType = (TrainingType) getArguments().getSerializable(TRAINING_TYPE_PARAM);

            id = getArguments().getInt(ID_PARAM);
            wordCardCount = getArguments().getInt(WORD_CARD_COUNT_PARAM);
            wrongAnswerWordCardIds = getArguments().getIntArray(WRONG_WORD_CARD_ID_ARRAY_PARAM);
        }
    }

    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.view_pager) ViewPager viewPager;

    /*
    @BindView(R.id.recycler_view_header_view) View recyclerViewHeaderView;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    */

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.word_card_result_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);

        viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {

            private static final int FRAGMENT_COUNT = 2;

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0: return ContentFragment.newInstance(trainingType, id, wordCardCount - wrongAnswerWordCardIds.length, wordCardCount);
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

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.init(trainingType, id, wordCardCount, wrongAnswerWordCardIds);
    }

    //@BindView(R.id.completed_text_view) TextView completedTextView;
    @Override
    public void setCompletedView(boolean completed, int wordCardCompletedCount, int wordCardCount) {
        //completedTextView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_badge, 0, 0);
        //completedTextView.setText(getString(R.string.rule_completed, wordCardCompletedCount, wordCardCount));
    }

    @Override
    public void setItems(@NonNull List<WordCard> wordCardList) {
        /*

        if (wordCardList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            recyclerViewHeaderView.setVisibility(View.GONE);
        } else {
            recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            recyclerView.setAdapter(new WordCardAdapter(wordCardList));
        }

        */
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
        //fragmentListener.requestToSetWordCardRulePointTrainingFragment(id);
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

    /*
    static class WordCardAdapter extends RecyclerView.Adapter<WordCardAdapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            @BindView(R.id.correct_word_text_view) TextView correctWordTextView;
            @BindView(R.id.incorrect_word_text_view) TextView incorrectWordTextView;

            @BindView(R.id.arrow_image_view) ImageView arrowImageView;
            @BindView(R.id.rule_description_text_view) TextView ruleDescriptionTextView;

            ViewHolder(View itemView) {
                super(itemView);

                ButterKnife.bind(this, itemView);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if (ruleDescriptionTextView.getVisibility() == View.VISIBLE) {
                    ruleDescriptionTextView.setVisibility(View.GONE);
                    arrowImageView.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                } else {
                    ruleDescriptionTextView.setVisibility(View.VISIBLE);
                    arrowImageView.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                }
            }
        }

        private final List<WordCard> wordCardList;
        WordCardAdapter(@NonNull List<WordCard> wordCardList) {
            this.wordCardList = wordCardList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_card_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            WordCard wordCard = wordCardList.get(position);

            holder.correctWordTextView.setText(wordCard.getCorrectWord());
            holder.incorrectWordTextView.setText(wordCard.getIncorrectWord());
            holder.ruleDescriptionTextView.setText(wordCard.getRulePoint().getDescription());
        }

        @Override
        public int getItemCount() {
            return wordCardList.size();
        }
    }
    */
}
