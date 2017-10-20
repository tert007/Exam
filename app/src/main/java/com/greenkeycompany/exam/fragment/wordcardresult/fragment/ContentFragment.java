package com.greenkeycompany.exam.fragment.wordcardresult.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.greenkeycompany.exam.R;
import com.greenkeycompany.exam.TrainingType;
import com.greenkeycompany.exam.repository.RealmRepository;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;

/**
 * Created by tert0 on 20.10.2017.
 */

public class ContentFragment extends Fragment {

    private RealmRepository realmRepository = new RealmRepository(Realm.getDefaultInstance());

    private static final String TRAINING_TYPE_PARAM = "training_type";
    private static final String ID_PARAM = "id";

    private static final String WORD_CARD_TRUE_ANSWER_COUNT_PARAM = "word_card_true_answer_count";
    private static final String WORD_CARD_COUNT_PARAM = "word_card_count";

    private TrainingType trainingType;
    private int id;
    private int trueAnswerWordCardCount;
    private int wordCardCount;

    public static ContentFragment newInstance(@NonNull TrainingType trainingType,
                                              int id,
                                              int trueAnswerWordCardCount,
                                              int wordCardCount) {

        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putSerializable(TRAINING_TYPE_PARAM, trainingType);
        args.putInt(ID_PARAM, id);
        args.putInt(WORD_CARD_TRUE_ANSWER_COUNT_PARAM, trueAnswerWordCardCount);
        args.putInt(WORD_CARD_COUNT_PARAM, wordCardCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            trainingType = (TrainingType) getArguments().getSerializable(TRAINING_TYPE_PARAM);
            id = getArguments().getInt(ID_PARAM);
            trueAnswerWordCardCount = getArguments().getInt(WORD_CARD_TRUE_ANSWER_COUNT_PARAM);
            wordCardCount = getArguments().getInt(WORD_CARD_COUNT_PARAM);
        }
    }

    @BindView(R.id.completed_text_view) TextView completedTextView;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.word_card_result_rule_point_content, container, false);

        unbinder = ButterKnife.bind(this, view);

        //boolean completed = TrainingCompletedUtil.isCompleted(trueAnswerWordCardCount, wordCardCount);
        completedTextView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_badge, 0, 0);
        completedTextView.setText(getString(R.string.rule_completed, trueAnswerWordCardCount, wordCardCount));

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
}
