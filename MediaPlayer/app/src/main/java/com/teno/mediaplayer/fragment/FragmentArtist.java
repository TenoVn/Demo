package com.teno.mediaplayer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teno.mediaplayer.app.App;
import com.teno.mediaplayer.item.ItemArtist;
import com.teno.mediaplayer.activities.MainActivity;
import com.teno.mediaplayer.R;
import com.teno.mediaplayer.adapter.ArtistAdapter;

import java.util.List;

/**
 * Created by Asus on 5/17/2017.
 */

public class FragmentArtist extends Fragment implements ArtistAdapter.IArtistAdapter {

    private ArtistAdapter mAdapter;
    private RecyclerView mRcvArtist;
    private List<ItemArtist> mListArtists;
    private TextView mTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fagment_category, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListArtists = ((App)getContext().getApplicationContext()).getListArtist();
        findViewByIds(view);
        showListArtist();
    }

    private void findViewByIds(View view) {
        mRcvArtist = (RecyclerView)view.findViewById(R.id.rcv_list);
        mTv = (TextView)view.findViewById(R.id.tv_list_in_category);
    }

    private void showListArtist() {
        mTv.setText("Tất cả ca sỹ");
        mAdapter = new ArtistAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRcvArtist.setLayoutManager(manager);
        mRcvArtist.setAdapter(mAdapter);
    }

    @Override
    public int getCount() {
        if (mListArtists == null) {
            return 0;
        }
        return mListArtists.size();
    }

    @Override
    public ItemArtist getItem(int position) {
        return mListArtists.get(position);
    }

    @Override
    public void onItemClick(int position) {
        ItemArtist artist = mListArtists.get(position);
        MainActivity main = (MainActivity)getActivity();
        main.showFragmentListSong("Artist", artist.getId());
    }
}
