package com.greenkeycompany.exam.fragment.trainingmenu.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greenkeycompany.exam.FragmentListener;
import com.greenkeycompany.exam.R;
import com.greenkeycompany.exam.fragment.trainingmenu.model.TrainingMenuModel;
import com.greenkeycompany.exam.fragment.trainingmenu.presenter.ITrainingMenuPresenter;
import com.greenkeycompany.exam.fragment.trainingmenu.presenter.TrainingMenuPresenter;
import com.greenkeycompany.exam.repository.RealmRepository;
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

public class TrainingMenuFragment extends MvpFragment<ITrainingMenuView, ITrainingMenuPresenter>
        implements ITrainingMenuView {

    private RealmRepository realmRepository = new RealmRepository(Realm.getDefaultInstance());

    @NonNull
    @Override
    public ITrainingMenuPresenter createPresenter() {
        return new TrainingMenuPresenter(realmRepository);
    }

    private static final String RULE_ID_PARAM = "rule_id";
    private int ruleId;

    public static TrainingMenuFragment newInstance(int ruleId) {
        TrainingMenuFragment fragment = new TrainingMenuFragment();
        Bundle args = new Bundle();
        args.putInt(RULE_ID_PARAM, ruleId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ruleId = getArguments().getInt(RULE_ID_PARAM);
        }
    }

    private TrainingAdapter adapter;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private View parentView;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.training_menu_fragment, container, false);

        unbinder = ButterKnife.bind(this, parentView);

        adapter = new TrainingAdapter(getContext(), new TrainingAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                presenter.onTrainingItemClick(position);
            }
        });

        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return parentView;
    }

    @Override
    public void setBackgroundColor(@ColorInt int color) {
        parentView.setBackgroundColor(color);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.init(ruleId);
    }

    @Override
    public void requestToSetWordCardRulePointTrainingFragment(int rulePointId) {
        fragmentListener.requestToSetWordCardRulePointTrainingFragment(rulePointId);
    }

    @Override
    public void setTrainingModelItems(@NonNull List<TrainingMenuModel> trainingMenuModelList) {
        adapter.setItemList(trainingMenuModelList);
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

    private static class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.ViewHolder> {

        interface ItemClickListener {
            void onItemClick(int position);
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            //@BindView(R.id.score_text_view) TextView scoreTextView;
            //@BindView(R.id.title_text_view) TextView titleTextView;

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

        private ItemClickListener itemClickListener;
        private final List<TrainingMenuModel> trainingMenuModelList = new ArrayList<>();

        TrainingAdapter(@NonNull Context context, @NonNull ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        void setItemList(@NonNull List<TrainingMenuModel> trainingMenuModelList) {
            this.trainingMenuModelList.addAll(trainingMenuModelList);
            this.notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.training_menu_item, parent, false);

            return new ViewHolder(view, itemClickListener);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            TrainingMenuModel trainingMenuModel = trainingMenuModelList.get(position);
        }

        @Override
        public int getItemCount() {
            return trainingMenuModelList.size();
        }
    }
}