package com.greenkeycompany.exam.fragment.chapter.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.greenkeycompany.exam.FragmentListener;
import com.greenkeycompany.exam.R;
import com.greenkeycompany.exam.fragment.chapter.presenter.IChapterPresenter;
import com.greenkeycompany.exam.fragment.chapter.presenter.ChapterPresenter;
import com.greenkeycompany.exam.repository.RealmRepository;
import com.greenkeycompany.exam.repository.model.Rule;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;

/**
 * Created by tert0 on 20.09.2017.
 */

public class ChapterFragment extends MvpFragment<IChapterView, IChapterPresenter>
        implements IChapterView {

    private RealmRepository realmRepository = new RealmRepository(Realm.getDefaultInstance());

    @NonNull
    @Override
    public IChapterPresenter createPresenter() {
        return new ChapterPresenter(realmRepository);
    }

    private static final String CHAPTER_ID_PARAM = "chapter_id";
    private int chapterId;

    public static ChapterFragment newInstance(int chapterId) {
        ChapterFragment fragment = new ChapterFragment();
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
        View view = inflater.inflate(R.layout.rule_list_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);

        adapter = new RuleAdapter(getContext(), new RuleAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                presenter.onRuleItemClick(position);
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
    public void setItemList(@NonNull List<Rule> ruleList) {
        adapter.setItemList(ruleList);
    }

    @Override
    public void requestToSetRuleFragment(int ruleId) {
        fragmentListener.requestToSetRuleFragment(ruleId);
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

        //private int greyColor;
        //private int greenColor;
        //private int orangeColor;

        private ItemClickListener itemClickListener;
        private final List<Rule> ruleModelList = new ArrayList<>();

        RuleAdapter(@NonNull Context context, @NonNull ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;

            //greyColor = ContextCompat.getReferenceColor(context, R.color.colorFirstChapter);
            //greenColor = ContextCompat.getReferenceColor(context, R.color.colorSecondChapter);
            //orangeColor = ContextCompat.getReferenceColor(context, R.color.colorThirdChapter);
        }

        void setItemList(@NonNull List<Rule> ruleModelList) {
            this.ruleModelList.addAll(ruleModelList);
            this.notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rule_item, parent, false);

            return new ViewHolder(view, itemClickListener);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Rule rule = ruleModelList.get(position);

            GradientDrawable shape = new GradientDrawable();
            shape.setShape(GradientDrawable.OVAL);

            //float score = rule.getScore();

            /*
            if (score == Rule.MIN_SCORE) shape.setColor(greyColor); else
            if (score == Rule.MAX_SCORE) shape.setColor(greenColor); else
                                         shape.setColor(orangeColor);
            */
            holder.scoreTextView.setBackground(shape);
            //holder.scoreTextView.setText(String.valueOf(score));
            holder.titleTextView.setText(rule.getTitle());
        }

        @Override
        public int getItemCount() {
            return ruleModelList.size();
        }
    }
}
