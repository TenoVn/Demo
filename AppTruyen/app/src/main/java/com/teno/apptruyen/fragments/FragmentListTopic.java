package com.teno.apptruyen.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teno.apptruyen.item.ItemDataTopic;
import com.teno.apptruyen.activities.MainActivity;
import com.teno.apptruyen.R;
import com.teno.apptruyen.adapters.TopicAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 4/24/2017.
 */

public class FragmentListTopic extends Fragment implements TopicAdapter.ITopicAdapter{

    private List<ItemDataTopic> mListTopics;
    private RecyclerView mRcTopic;
    private TopicAdapter mTopicAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_topic, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        mRcTopic = (RecyclerView)view.findViewById(R.id.rcv_list_topic);
        mTopicAdapter = new TopicAdapter(this);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        mRcTopic.setLayoutManager(manager);
        mRcTopic.setAdapter(mTopicAdapter);
    }

    private void initData() {
        mListTopics = new ArrayList<>();
        mListTopics.add(new ItemDataTopic(R.drawable.im_00, "Vova"));
        mListTopics.add(new ItemDataTopic(R.drawable.im_01, "Dân gian"));
        mListTopics.add(new ItemDataTopic(R.drawable.im_02, "Gia đình"));
        mListTopics.add(new ItemDataTopic(R.drawable.im_03, "Tình yêu"));
        mListTopics.add(new ItemDataTopic(R.drawable.im_04, "Tiếu lâm"));
        mListTopics.add(new ItemDataTopic(R.drawable.im_05, "Công sở"));
        mListTopics.add(new ItemDataTopic(R.drawable.im_06, "Học sinh"));
        mListTopics.add(new ItemDataTopic(R.drawable.im_07, "Truyện ngắn"));
        mListTopics.add(new ItemDataTopic(R.drawable.im_08, "Cười 18+"));
        mListTopics.add(new ItemDataTopic(R.drawable.im_09, "Y học"));
        mListTopics.add(new ItemDataTopic(R.drawable.im_10, "Tam quốc"));
        mListTopics.add(new ItemDataTopic(R.drawable.im_11, "Thế giới"));
        mListTopics.add(new ItemDataTopic(R.drawable.im_12, "Tây du ký chế"));
        mListTopics.add(new ItemDataTopic(R.drawable.im_13, "Truyện khác"));
    }

    @Override
    public int getCount() {
        return mListTopics.size();
    }

    @Override
    public ItemDataTopic getData(int position) {
        return mListTopics.get(position);
    }

    @Override
    public void onItemClick(int position) {
        ItemDataTopic dataTopic = mListTopics.get(position);
        String topicName = dataTopic.getNameTopic();
        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.showFragmentListStory(topicName);
    }

}
