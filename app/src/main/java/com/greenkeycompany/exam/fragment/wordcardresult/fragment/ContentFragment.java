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
import com.greenkeycompany.exam.fragment.ScoreUtil;
import com.greenkeycompany.exam.fragment.TrainingCompletedUtil;
import com.greenkeycompany.exam.repository.RealmRepository;
import com.greenkeycompany.exam.repository.model.result.ChapterExamResult;
import com.greenkeycompany.exam.repository.model.result.WordCardSetResult;
import com.greenkeycompany.exam.repository.model.result.RuleExamResult;

import io.realm.Realm;

/**
 * Created by tert0 on 20.10.2017.
 */

public class ContentFragment extends Fragment {

    private RealmRepository realmRepository = new RealmRepository(Realm.getDefaultInstance());

    private static final String TRAINING_TYPE_PARAM = "training_type";
    private static final String RESULT_ID_PARAM = "result_id";

    private TrainingType trainingType;
    private int resultId;

    public static ContentFragment newInstance(@NonNull TrainingType trainingType,
                                              int resultId) {

        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putSerializable(TRAINING_TYPE_PARAM, trainingType);
        args.putInt(RESULT_ID_PARAM, resultId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            trainingType = (TrainingType) getArguments().getSerializable(TRAINING_TYPE_PARAM);
            resultId = getArguments().getInt(RESULT_ID_PARAM);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;

        switch (trainingType) {
            case WORD_CARD_SET_TRAINING: {
                view = inflater.inflate(R.layout.word_card_result_rule_point_content, container, false);

                WordCardSetResult result = realmRepository.getWordCardSetResult(resultId);
                WordCardSetResult bestResult = realmRepository.getBestWordCardSetResult(result.getWordCardSetId());

                int wordCardCount = realmRepository.getWordCardSet(result.getWordCardSetId()).getSize();

                TextView resultTextView = view.findViewById(R.id.result_text_view);
                resultTextView.setText(getString(R.string.training_result, result.getWordCardCompletedCount(), wordCardCount));

                if (result.getWordCardCompletedCount() == wordCardCount) {
                    resultTextView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.result_completed_fully_icon, 0, 0);
                } else if (TrainingCompletedUtil.isCompleted(result.getWordCardCompletedCount(), wordCardCount)) {
                    resultTextView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.result_completed_icon, 0, 0);
                } else {
                    resultTextView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.result_uncompleted_icon, 0, 0);
                }

                TextView bestResultTextView = view.findViewById(R.id.best_result_text_view);
                bestResultTextView.setText(getString(R.string.training_best_result, bestResult.getWordCardCompletedCount(), wordCardCount));
            }
            break;
            case RULE_EXAM: {
                view = inflater.inflate(R.layout.word_card_result_content, container, false);

                RuleExamResult result = realmRepository.getRuleExamResult(resultId);
                RuleExamResult bestResult = realmRepository.getBestRuleExamResult(result.getRuleId());

                TextView resultTextView = view.findViewById(R.id.result_text_view);
                resultTextView.setText(getString(R.string.training_score, ScoreUtil.convertScoreToString(result.getScore())));

                if (result.getScore() == ScoreUtil.MAX_SCORE) {
                    resultTextView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.result_completed_fully_icon, 0, 0);
                } else if (result.getScore() >= ScoreUtil.COMPLETED_SCORE) {
                    resultTextView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.result_completed_icon, 0, 0);
                } else {
                    resultTextView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.result_uncompleted_icon, 0, 0);
                }

                TextView bestResultTextView = view.findViewById(R.id.best_result_text_view);
                bestResultTextView.setText(getString(R.string.training_best_score, ScoreUtil.convertScoreToString(bestResult.getScore())));
            }
            break;
            case CHAPTER_EXAM: {
                view = inflater.inflate(R.layout.word_card_result_content, container, false);

                ChapterExamResult result = realmRepository.getChapterExamResult(resultId);
                ChapterExamResult bestResult = realmRepository.getBestChapterExamResult(result.getChapterId());

                TextView resultTextView = view.findViewById(R.id.result_text_view);
                resultTextView.setText(getString(R.string.training_score, ScoreUtil.convertScoreToString(result.getScore())));

                if (result.getScore() == ScoreUtil.MAX_SCORE) {
                    resultTextView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.result_completed_fully_icon, 0, 0);
                } else if (result.getScore() >= ScoreUtil.COMPLETED_SCORE) {
                    resultTextView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.result_completed_icon, 0, 0);
                } else {
                    resultTextView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.result_uncompleted_icon, 0, 0);
                }

                TextView bestResultTextView = view.findViewById(R.id.best_result_text_view);
                bestResultTextView.setText(getString(R.string.training_best_score, ScoreUtil.convertScoreToString(bestResult.getScore())));
            }
            break;
        }

        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        realmRepository.close();
    }
}
