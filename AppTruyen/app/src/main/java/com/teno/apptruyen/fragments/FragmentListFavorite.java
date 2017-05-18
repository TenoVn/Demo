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
import android.widget.Toast;

import com.teno.apptruyen.database.DatabaseManager;
import com.teno.apptruyen.adapters.FavoriteAdapter;
import com.teno.apptruyen.item.ItemDataStory;
import com.teno.apptruyen.activities.MainActivity;
import com.teno.apptruyen.R;

import java.util.List;

/**
 * Created by Asus on 4/24/2017.
 */

public class FragmentListFavorite extends Fragment implements FavoriteAdapter.IFavoriteAdapter {

    private RecyclerView mRcListStory;
    private FavoriteAdapter mAdapter;
    private List<ItemDataStory> mListFavorite;
    private TextView mTvNothing;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_favorite, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewByIds(view);
        DatabaseManager databaseManager = new DatabaseManager(getContext());
        mListFavorite = databaseManager.getListFavorite();
        if(mListFavorite.size()==0){
            mTvNothing.setVisibility(View.VISIBLE);
        }
        else
            mTvNothing.setVisibility(View.GONE);
        createRecyclerView();
    }

    private void findViewByIds(View view) {
        mRcListStory = (RecyclerView)view.findViewById(R.id.rcv_list_favorite);
        mTvNothing = (TextView)view.findViewById(R.id.tv_nothing_favorite);
    }

    private void createRecyclerView(){
        mAdapter = new FavoriteAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRcListStory.setLayoutManager(manager);
        mRcListStory.setAdapter(mAdapter);
    }

    @Override
    public int getCount() {
        return mListFavorite.size();
    }

    @Override
    public ItemDataStory getStoryData(int position) {
        return mListFavorite.get(position);
    }

    @Override
    public void onItemClick(int position, int id) {
        switch (id){
            case R.id.tv_story:
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.showFragmentContentStory("Favorite", position);
                break;
            case R.id.iv_delete:
                DatabaseManager databaseManager = new DatabaseManager(getContext());
                databaseManager.updateFavorite(mListFavorite.get(position).getIdStory(), 0);
                mListFavorite.remove(position);
                mAdapter.notifyDataSetChanged();
                if(mListFavorite.size()==0){
                    mTvNothing.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }
}
