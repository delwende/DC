package com.aikinggroup.diabetecontrole.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aikinggroup.diabetecontrole.R;
import com.aikinggroup.diabetecontrole.activity.AddGlucoseActivity;
import com.aikinggroup.diabetecontrole.activity.MainActivity;
import com.aikinggroup.diabetecontrole.adapter.AssistantAdapter;
import com.aikinggroup.diabetecontrole.object.ActionTip;
import com.aikinggroup.diabetecontrole.presenter.AssistantPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AssistantFragment extends Fragment {

    private SharedPreferences sharedPref;

    @BindView(R.id.fragment_tips_recyclerview)
    RecyclerView tipsRecycler;
    @BindView(R.id.fragment_assistant_archived)
    LinearLayout archivedButton;
    @BindView(R.id.fragment_assistant_archived_dismiss)
    LinearLayout archivedDismissButton;

    private AssistantAdapter adapter;
    private ArrayList<ActionTip> actionTips;
    private String[] actionTipTitles;
    private String[] actionTipDescriptions;
    private String[] actionTipActions;

    private Unbinder unbinder;

    public static AssistantFragment newInstance() {
        return new AssistantFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AssistantPresenter presenter = new AssistantPresenter(this);
        actionTips = new ArrayList<>();

        actionTipTitles = getResources().getStringArray(R.array.assistant_titles);
        actionTipDescriptions = getResources().getStringArray(R.array.assistant_descriptions);
        actionTipActions = getResources().getStringArray(R.array.assistant_actions);
        populateWithNewTips();

        View view = inflater.inflate(R.layout.fragment_assistant, container, false);
        unbinder = ButterKnife.bind(this, view);

        adapter = new AssistantAdapter(presenter, getActivity().getApplicationContext().getResources(), actionTips);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        tipsRecycler.setLayoutManager(llm);
        tipsRecycler.setAdapter(adapter);
        tipsRecycler.setHasFixedSize(false);

        initSwipeToRemoveTouchHelper();

        // If there aren't dismissed tips, don't show archive button
        if (actionTipTitles.length == adapter.getItemCount()) {
            archivedButton.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();

        super.onDestroyView();
    }

    // Swipe to remove functionality
    private void initSwipeToRemoveTouchHelper() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                if (archivedDismissButton.getVisibility() == View.VISIBLE) {
                    // If we're in archive, restore tips
                    TextView title = (TextView) viewHolder.itemView.findViewById(R.id.fragment_assistant_item_title);
                    removePreference(title.getText().toString());

                    int position = viewHolder.getAdapterPosition();
                    actionTips.remove(position);
                    adapter.notifyDataSetChanged();
                } else {
                    // Else archive them
                    TextView title = (TextView) viewHolder.itemView.findViewById(R.id.fragment_assistant_item_title);
                    addPreference(title.getText().toString());

                    int position = viewHolder.getAdapterPosition();
                    actionTips.remove(position);
                    adapter.notifyDataSetChanged();

                    ((MainActivity) getActivity()).reloadFragmentAdapter();
                }
            }
        };

        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        itemTouchHelper.attachToRecyclerView(tipsRecycler);
    }

    @OnClick(R.id.fragment_assistant_archived_dismiss)
    void archivedDismissButtonClick() {
        populateWithNewTips();
        adapter.notifyDataSetChanged();
        tipsRecycler.swapAdapter(adapter, false);
        archivedDismissButton.setVisibility(View.GONE);
        archivedButton.setVisibility(View.VISIBLE);

        ((MainActivity) getActivity()).reloadFragmentAdapter();
    }

    @OnClick(R.id.fragment_assistant_archived)
    void archivedButtonClicked() {
        populateWithArchivedTips();
        adapter.notifyDataSetChanged();
        tipsRecycler.swapAdapter(adapter, false);
        archivedDismissButton.setVisibility(View.VISIBLE);
        final Animation slide = new TranslateAnimation(0, 0, 0, 200);
        slide.setDuration(500);

        archivedButton.startAnimation(slide);
        archivedButton.setVisibility(View.GONE);
    }

    private void populateWithNewTips() {
        actionTips.clear();
        for (int i = 0; i < actionTipTitles.length; i++) {
            String actionTipTitle = actionTipTitles[i];
            String actionTipDescription = actionTipDescriptions[i];
            String actionTipAction = actionTipActions[i];

            ActionTip actionTip = new ActionTip();
            actionTip.setTipTitle(actionTipTitle);
            actionTip.setTipDescription(actionTipDescription);
            actionTip.setTipAction(actionTipAction);

            Boolean value = sharedPref.getBoolean(actionTipTitle, false);
            if (!value) {
                actionTips.add(actionTip);
            }
        }
    }

    private void populateWithArchivedTips() {
        actionTips.clear();
        for (int i = 0; i < actionTipTitles.length; i++) {
            String actionTipTitle = actionTipTitles[i];
            String actionTipDescription = actionTipDescriptions[i];
            String actionTipAction = actionTipActions[i];

            ActionTip actionTip = new ActionTip();
            actionTip.setTipTitle(actionTipTitle);
            actionTip.setTipDescription(actionTipDescription);
            actionTip.setTipAction(actionTipAction);

            Boolean value = sharedPref.getBoolean(actionTipTitle, false);
            if (value) {
                actionTips.add(actionTip);
            }
        }
    }

    private void addPreference(String key) {
        sharedPref.edit().putBoolean(key, true).apply();
    }

    private void removePreference(String key) {
        sharedPref.edit().putBoolean(key, false).apply();
    }

    public void addReading() {
        Intent intent = new Intent(getActivity(), AddGlucoseActivity.class);
        startActivity(intent);
        getActivity().finish();
    }


    public void openSupportDialog() {
        ((MainActivity) getActivity()).openSupportDialog();
    }
}