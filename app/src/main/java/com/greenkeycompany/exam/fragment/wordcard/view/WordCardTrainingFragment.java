package com.greenkeycompany.exam.fragment.wordcard.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greenkeycompany.exam.FragmentListener;
import com.greenkeycompany.exam.R;
import com.greenkeycompany.exam.TrainingType;
import com.greenkeycompany.exam.fragment.ScoreUtil;
import com.greenkeycompany.exam.fragment.wordcard.presenter.IWordCardTrainingPresenter;
import com.greenkeycompany.exam.fragment.wordcard.presenter.WordCardTrainingPresenter;
import com.greenkeycompany.exam.repository.RealmRepository;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;

/**
 * Created by tert0 on 20.09.2017.
 */

public class WordCardTrainingFragment extends MvpFragment<IWordCardTrainingView, IWordCardTrainingPresenter>
        implements IWordCardTrainingView {

    private RealmRepository realmRepository = new RealmRepository(Realm.getDefaultInstance());

    @NonNull
    @Override
    public IWordCardTrainingPresenter createPresenter() {
        return new WordCardTrainingPresenter(realmRepository);
    }

    private static final String TRAINING_TYPE_PARAM = "training_type";
    private static final String ID_PARAM = "id";

    private TrainingType trainingType;
    private int id;

    /*
    public static WordCardTrainingFragment newFinalTrainingInstance() {
        return newInstance(TrainingType.FINAL, 0);
    }

    public static WordCardTrainingFragment newChapterTrainingInstance(int chapterId) {
        return newInstance(TrainingType.CHAPTER, chapterId);
    }

    public static WordCardTrainingFragment newRuleTrainingInstance(int ruleId) {
        return newInstance(TrainingType.RULE, ruleId);
    }

    public static WordCardTrainingFragment newRulePointTrainingInstance(int rulePointId) {
        return newInstance(TrainingType.RULE_POINT, rulePointId);
    }
    */

    public static WordCardTrainingFragment newInstance(@NonNull TrainingType trainingType, int id) {
        WordCardTrainingFragment fragment = new WordCardTrainingFragment();
        Bundle args = new Bundle();
        args.putSerializable(TRAINING_TYPE_PARAM, trainingType);
        args.putInt(ID_PARAM, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            trainingType = (TrainingType) getArguments().getSerializable(TRAINING_TYPE_PARAM);
            id = getArguments().getInt(ID_PARAM);
        }
    }

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.word_card_training_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);

        //setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.initTraining(trainingType, id);
    }

    @BindView(R.id.progress_layout) LinearLayout progressLayout;

    private List<View> progressItemList;

    private static final int ITEMS_PER_LINE = 10;

    @BindDimen(R.dimen.word_card_training_progress_item_height) int progressItemHeight;
    @BindDimen(R.dimen.word_card_training_progress_item_margin) int progressItemMargin;

    @BindColor(R.color.colorRed) int colorRed;
    @BindColor(R.color.colorGrey) int colorGrey;
    @BindColor(R.color.colorGreen) int colorGreen;

    @Override
    public void initProgressView(int itemCount) {
        progressItemList = new ArrayList<>(itemCount);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(0, progressItemHeight, 1f);
        itemParams.setMargins(progressItemMargin, progressItemMargin, progressItemMargin, progressItemMargin);

        int lineCount = itemCount / ITEMS_PER_LINE;

        for (int i = 0; i < lineCount; i++) {
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            for (int j = 0; j < ITEMS_PER_LINE; j++) {
                View item = new View(getContext());
                item.setLayoutParams(itemParams);
                item.setBackgroundColor(colorGrey);

                linearLayout.addView(item);
                progressItemList.add(item);
            }

            progressLayout.addView(linearLayout);
        }
    }

    @BindView(R.id.score_text_view) TextView scoreTextView;
    @Override
    public void setScoreView(int trueAnswerCount, int wordCardCount) {
        scoreTextView.setText(ScoreUtil.getScoreByString(trueAnswerCount, wordCardCount));
    }

    @Override
    public void setProgressViewItem(int index, boolean trueAnswer) {
        progressItemList.get(index).setBackgroundColor(trueAnswer ? colorGreen : colorRed);
    }

    @BindView(R.id.word_text_view) TextView wordTextView;
    @Override
    public void setWordView(String word) {
        wordTextView.setText(word);
        wordTextView.setBackgroundResource(R.drawable.word_card_training_answer_background);
    }

    @Override
    public void setWordResultView(boolean trueAnswer) {
        wordTextView.setBackgroundResource(trueAnswer ?
                        R.drawable.word_card_training_true_answer_background :
                        R.drawable.word_card_training_false_answer_background);
    }

    @BindView(R.id.correct_word_text_view) TextView correctWordTextView;
    @Override
    public void setCorrectWordView(String correctWord) {
        correctWordTextView.setText(getString(R.string.word_card_training_correct_word, correctWord));
    }

    @Override
    public void setCorrectWordViewVisibility(boolean visible) {
        correctWordTextView.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    @BindView(R.id.next_view) View nextView;
    @Override
    public void setNextViewVisibility(boolean visible) {
        nextView.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    @OnClick(R.id.next_view)
    public void onNextViewClick() {
        presenter.onNextClick();
    }

    @BindViews({R.id.false_answer_view, R.id.true_answer_view}) List<View> answerViewList;
    @Override
    public void setAnswersButtonVisibility(boolean visible) {
        for (View view : answerViewList) {
            view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @OnClick(R.id.false_answer_view)
    public void onFalseAnswerViewClick() {
        presenter.onFalseAnswerClick();
    }

    @OnClick(R.id.true_answer_view)
    public void onTrueAnswerViewClick() {
        presenter.onTrueAnswerClick();
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

    /*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.training_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.help:
                //fragmentListener.requestToSetFragment(FragmentType.RULE_CONTENT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    */

    @Override
    public void requestToSetResultFragment(@NonNull TrainingType trainingType, int resultId, int[] wrongAnswerWordCardIds) {
        fragmentListener.requestToSetWordCardResultFragment(trainingType, resultId, wrongAnswerWordCardIds);
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
