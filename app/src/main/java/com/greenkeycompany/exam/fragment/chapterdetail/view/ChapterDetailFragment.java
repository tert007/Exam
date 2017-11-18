package com.greenkeycompany.exam.fragment.chapterdetail.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.greenkeycompany.exam.FragmentListener;
import com.greenkeycompany.exam.R;
import com.greenkeycompany.exam.TrainingType;
import com.greenkeycompany.exam.activity.view.MainActivity;
import com.greenkeycompany.exam.fragment.ScoreUtil;
import com.greenkeycompany.exam.fragment.chapterdetail.model.RuleMenuItem;
import com.greenkeycompany.exam.fragment.chapterdetail.presenter.IChapterDetailPresenter;
import com.greenkeycompany.exam.fragment.chapterdetail.presenter.ChapterDetailPresenter;
import com.greenkeycompany.exam.repository.RealmRepository;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;

/**
 * Created by tert0 on 20.09.2017.
 */

public class ChapterDetailFragment extends MvpFragment<IChapterDetailView, IChapterDetailPresenter>
        implements IChapterDetailView {

    private RealmRepository realmRepository = new RealmRepository(Realm.getDefaultInstance());

    @NonNull
    @Override
    public IChapterDetailPresenter createPresenter() {
        return new ChapterDetailPresenter(realmRepository);
    }

    private static final String CHAPTER_ID_PARAM = "chapter_id";
    private int chapterId;

    public static ChapterDetailFragment newInstance(int chapterId) {
        ChapterDetailFragment fragment = new ChapterDetailFragment();
        Bundle args = new Bundle();
        args.putInt(CHAPTER_ID_PARAM, chapterId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            chapterId = getArguments().getInt(CHAPTER_ID_PARAM);
        }
    }

    private RuleAdapter adapter;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chapter_detail_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);

        adapter = new RuleAdapter(getContext(), new RuleAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                presenter.onRuleMenuItemClick(position);
            }
        });

        recyclerView.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.init(chapterId);
    }

    @Override
    public void requestToSetActionBarTitle(String title) {
        fragmentListener.requestToSetActionBarTitle(title);
    }

    @BindView(R.id.chapter_view) View chapterView;
    @Override
    public void setChapterViewColor(@ColorInt int color) {
        chapterView.setBackgroundColor(color);
    }

    @BindView(R.id.chapter_score_text_view) TextView scoreTextView;
    @Override
    public void setChapterScore(float score) {
        scoreTextView.setText(ScoreUtil.convertScoreToString(score));
    }

    @OnClick(R.id.chapter_start_training_text_view)
    public void onStartChapterTrainingViewClick() {
        presenter.onStartChapterTrainingClick();
    }

    @Override
    public void setRuleItemList(@NonNull List<RuleMenuItem> ruleList) {
        adapter.setItemList(ruleList);
    }

    @Override
    public void showChapterTrainingLockedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(R.string.chapter_exam_locked_title);
        builder.setMessage(R.string.chapter_exam_locked_message);

        builder.setPositiveButton(R.string.locked_close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void requestToStartChapterTraining(int chapterId) {
        fragmentListener.requestToSetWordCardTrainingFragment(TrainingType.CHAPTER_EXAM, chapterId);
    }

    @Override
    public void requestToSetRuleDetailFragment(int ruleId) {
        fragmentListener.requestToSetRuleDetailFragment(ruleId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realmRepository.close();
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


    static class RuleAdapter extends RecyclerView.Adapter<RuleAdapter.ViewHolder> {

        interface ItemClickListener {
            void onItemClick(int position);
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            @BindView(R.id.score_text_view) TextView scoreTextView;
            @BindView(R.id.title_text_view) TextView titleTextView;
            @BindView(R.id.subtitle_text_view) TextView subtitleTextView;

            private ItemClickListener itemClickListener;

            ViewHolder(View itemView, @NonNull ItemClickListener itemClickListener) {
                super(itemView);

                ButterKnife.bind(this, itemView);

                this.itemClickListener = itemClickListener;
                this.itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(getAdapterPosition());
            }
        }

        private Context context;
        private ItemClickListener itemClickListener;
        private final List<RuleMenuItem> ruleMenuItemList = new ArrayList<>();

        RuleAdapter(@NonNull Context context, @NonNull ItemClickListener itemClickListener) {
            this.context = context;
            this.itemClickListener = itemClickListener;
        }

        void setItemList(@NonNull List<RuleMenuItem> ruleMenuItemList) {
            this.ruleMenuItemList.addAll(ruleMenuItemList);
            this.notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_detail_rule_item, parent, false);

            return new ViewHolder(view, itemClickListener);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            RuleMenuItem ruleMenuItem = ruleMenuItemList.get(position);

            holder.titleTextView.setText(ruleMenuItem.getTitle());
            holder.subtitleTextView.setText(context.getString(R.string.rule_completed,
                    ruleMenuItem.getWordCardCompletedCount(),
                    ruleMenuItem.getWordCardCount()));

            holder.scoreTextView.setText(ScoreUtil.convertScoreToString(ruleMenuItem.getScore()));
            holder.scoreTextView.setTextColor(ScoreUtil.getReferenceColor(ruleMenuItem.getScore()));
        }

        @Override
        public int getItemCount() {
            return ruleMenuItemList.size();
        }
    }
}
