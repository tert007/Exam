package com.greenkeycompany.exam.fragment.wordcardresult.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.greenkeycompany.exam.R;
import com.greenkeycompany.exam.repository.RealmRepository;
import com.greenkeycompany.exam.repository.model.WordCard;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;

/**
 * Created by tert0 on 20.10.2017.
 */

public class WordListFragment extends Fragment {

    private RealmRepository realmRepository = new RealmRepository(Realm.getDefaultInstance());

    private static final String WRONG_WORD_CARD_ID_ARRAY_PARAM = "wrong_answer_word_card_ids";
    private int[] wrongAnswerWordCardIds;

    public static WordListFragment newInstance(int[] wrongAnswerWordCardIds) {
        WordListFragment fragment = new WordListFragment();
        Bundle args = new Bundle();
        args.putIntArray(WRONG_WORD_CARD_ID_ARRAY_PARAM, wrongAnswerWordCardIds);
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

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.empty_view) View emptyView;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.word_card_list_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);

        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                List<WordCard> wordCardList = new ArrayList<>(wrongAnswerWordCardIds.length);
                for (int wordCardId : wrongAnswerWordCardIds) {
                    wordCardList.add(realmRepository.getWordCard(wordCardId));
                }

                if (wordCardList.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);

                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                    recyclerView.setAdapter(new WordCardAdapter(wordCardList));
                }
            }
        });

        return view;
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
}
