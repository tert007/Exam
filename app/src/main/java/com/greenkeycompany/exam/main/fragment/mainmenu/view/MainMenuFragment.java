package com.greenkeycompany.exam.main.fragment.mainmenu.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.greenkeycompany.exam.main.activity.view.FragmentListener;
import com.greenkeycompany.exam.R;
import com.greenkeycompany.exam.main.TrainingType;
import com.greenkeycompany.exam.main.activity.view.ActionBarView;
import com.greenkeycompany.exam.app.PremiumUtil;
import com.greenkeycompany.exam.main.util.ChapterUtil;
import com.greenkeycompany.exam.main.util.ScoreUtil;
import com.greenkeycompany.exam.main.fragment.mainmenu.model.ChapterMenuItem;
import com.greenkeycompany.exam.main.fragment.mainmenu.presenter.IMainMenuPresenter;
import com.greenkeycompany.exam.main.fragment.mainmenu.presenter.MainMenuPresenter;
import com.greenkeycompany.exam.main.repository.RealmRepository;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;

/**
 * Created by tert0 on 04.10.2017.
 */

public class MainMenuFragment extends MvpFragment<IMainMenuView, IMainMenuPresenter>
        implements IMainMenuView {

    private ActionBarView actionBarView;

    private RealmRepository realmRepository = new RealmRepository(Realm.getDefaultInstance());

    @NonNull
    @Override
    public IMainMenuPresenter createPresenter() {
        return new MainMenuPresenter(realmRepository);
    }

    public static MainMenuFragment newInstance() {
        return new MainMenuFragment();
    }

    private Unbinder unbinder;

    private ChapterAdapter adapter;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_menu_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);

        adapter = new ChapterAdapter(getContext(), new ChapterAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                presenter.onChapterItemClick(position);
            }
        });

        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        actionBarView = (ActionBarView) getActivity();
        actionBarView.setActionBarTitle(getString(R.string.app_name));
        actionBarView.setActionBarHomeButtonVisibility(false);
        actionBarView.setActionBarPremiumButtonVisibility( ! PremiumUtil.isPremiumUser());

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.init();
    }

    @Override
    public void setChapters(@NonNull List<ChapterMenuItem> chapterMenuItemList) {
        adapter.setItemList(chapterMenuItemList);
    }

    @Override
    public void requestToSetChapterFragment(int chapterId) {
        fragmentListener.requestToSetChapterDetailFragment(chapterId);
    }

    @Override
    public void requestToSetFinalExamFragment() {
        fragmentListener.requestToSetWordCardTrainingFragment(TrainingType.FINAL_EXAM, 0);
    }

    @Override
    public void onDestroy() {
        realmRepository.close();
        super.onDestroy();
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

    static class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolder> {

        interface ItemClickListener {
            void onItemClick(int position);
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            @BindView(R.id.score_text_view) TextView scoreTextView;
            @BindView(R.id.title_text_view) TextView titleTextView;
            @BindView(R.id.start_text_view) TextView startTextView;

            private ItemClickListener itemClickListener;

            ViewHolder(View itemView, @NonNull ItemClickListener itemClickListener) {
                super(itemView);

                ButterKnife.bind(this, itemView);

                this.itemClickListener = itemClickListener;
                this.startTextView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(getAdapterPosition());
            }
        }

        private Context context;
        private ItemClickListener itemClickListener;
        private final List<ChapterMenuItem> chapterMenuItemList = new ArrayList<>();

        ChapterAdapter(@NonNull Context context, @NonNull ItemClickListener itemClickListener) {
            this.context = context;
            this.itemClickListener = itemClickListener;
        }

        void setItemList(@NonNull List<ChapterMenuItem> chapterMenuItemList) {
            this.chapterMenuItemList.addAll(chapterMenuItemList);
            this.notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_menu_chapter_item, parent, false);

            return new ViewHolder(view, itemClickListener);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ChapterMenuItem chapterMenuItem = chapterMenuItemList.get(position);

            if (chapterMenuItem.getId() == ChapterUtil.FINAL_EXAM_ID) {
                holder.titleTextView.setText(context.getString(R.string.final_exam));
                holder.startTextView.setText(context.getString(R.string.final_exam_start));
            } else {
                holder.titleTextView.setText(chapterMenuItem.getTitle());
                holder.startTextView.setText(context.getString(R.string.chapter_start));
            }

            holder.itemView.setBackgroundColor(ChapterUtil.getColor(chapterMenuItem.getId()));

            holder.scoreTextView.setText(ScoreUtil.convertScoreToString(chapterMenuItem.getScore()));
        }

        @Override
        public int getItemCount() {
            return chapterMenuItemList.size();
        }
    }
}
