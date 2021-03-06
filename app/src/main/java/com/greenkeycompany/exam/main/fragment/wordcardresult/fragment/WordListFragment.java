package com.greenkeycompany.exam.main.fragment.wordcardresult.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.greenkeycompany.exam.R;
import com.greenkeycompany.exam.main.repository.RealmRepository;
import com.greenkeycompany.exam.main.repository.model.RulePoint;
import com.greenkeycompany.exam.main.repository.model.WordCard;

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

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.word_card_list_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);

        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                List<WordCardResultItem> wordCardResultItemList = new ArrayList<>(wrongAnswerWordCardIds.length);
                for (int wordCardId : wrongAnswerWordCardIds) {
                    WordCard wordCard = realmRepository.getWordCard(wordCardId);
                    RulePoint rulePoint = realmRepository.getRulePoint(wordCard.getRulePointId());

                    WordCardResultItem item = new WordCardResultItem();
                    item.setCorrectWord(wordCard.getCorrectWord());
                    item.setIncorrectWord(wordCard.getIncorrectWord());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        item.setRuleDescription(Html.fromHtml(rulePoint.getDescription(), Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        item.setRuleDescription(Html.fromHtml(rulePoint.getDescription()));
                    }

                    wordCardResultItemList.add(item);
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                recyclerView.setAdapter(new WordCardAdapter(wordCardResultItemList));
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

    static class WordCardResultItem {
        private String correctWord;
        private String incorrectWord;

        private CharSequence ruleDescription;

        public String getCorrectWord() {
            return correctWord;
        }

        public void setCorrectWord(String correctWord) {
            this.correctWord = correctWord;
        }

        public String getIncorrectWord() {
            return incorrectWord;
        }

        public void setIncorrectWord(String incorrectWord) {
            this.incorrectWord = incorrectWord;
        }

        public CharSequence getRuleDescription() {
            return ruleDescription;
        }

        public void setRuleDescription(CharSequence ruleDescription) {
            this.ruleDescription = ruleDescription;
        }
    }

    static class WordCardAdapter extends RecyclerView.Adapter<WordCardAdapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            @BindView(R.id.incorrect_word_message_text_view) TextView correctWordTextView;
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

        private final List<WordCardResultItem> wordCardList;
        WordCardAdapter(@NonNull List<WordCardResultItem> wordCardList) {
            this.wordCardList = wordCardList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_card_list_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            WordCardResultItem wordCardResultItem = wordCardList.get(position);

            holder.correctWordTextView.setText(wordCardResultItem.getCorrectWord());
            holder.incorrectWordTextView.setText(wordCardResultItem.getIncorrectWord());
            holder.ruleDescriptionTextView.setText(wordCardResultItem.getRuleDescription());
        }

        @Override
        public int getItemCount() {
            return wordCardList.size();
        }
    }
}
