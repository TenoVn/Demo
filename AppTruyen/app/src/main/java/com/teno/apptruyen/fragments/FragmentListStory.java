package com.teno.apptruyen.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teno.apptruyen.app.App;
import com.teno.apptruyen.item.ItemDataStory;
import com.teno.apptruyen.activities.MainActivity;
import com.teno.apptruyen.R;
import com.teno.apptruyen.adapters.StoryAdapter;

import java.util.List;

/**
 * Created by Asus on 4/24/2017.
 */

public class FragmentListStory extends Fragment implements StoryAdapter.IStoryAdapter, View.OnClickListener {

    private RecyclerView mRcListStory;
    private StoryAdapter mAdapter;
    private List<ItemDataStory> mListStory;
    private TextView mTvTopic;
    private String mTopicName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_story, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewByIds(view);
        getTopicNameInBundle();
        mListStory = ((App)getContext().getApplicationContext()).getListStory(mTopicName);
        createRecyclerView();
    }

    private void findViewByIds(View view) {
        mRcListStory = (RecyclerView)view.findViewById(R.id.rcv_list_story);
        mTvTopic = (TextView)view.findViewById(R.id.tv_title_topic);
        view.findViewById(R.id.btn_back).setOnClickListener(this);
    }

    private void getTopicNameInBundle() {
        Bundle bundle = getArguments();
        mTopicName = bundle.getString("Topic name");
        mTvTopic.setText(mTopicName);
    }

    private void createRecyclerView(){
        mAdapter = new StoryAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRcListStory.setLayoutManager(manager);
        mRcListStory.setAdapter(mAdapter);
        mRcListStory.smoothScrollToPosition(mListStory.size()-1);
    }

    @Override
    public int getCount() {
        return mListStory.size();
    }

    @Override
    public ItemDataStory getStoryData(int position) {
        return mListStory.get(position);
    }

    @Override
    public void onItemClick(int position) {
        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.showFragmentContentStory(mTopicName, position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.getSupportFragmentManager().popBackStack();
                break;
            default:
                break;
        }
    }
}
