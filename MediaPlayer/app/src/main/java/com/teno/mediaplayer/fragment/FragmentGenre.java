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
import com.teno.mediaplayer.item.ItemGenre;
import com.teno.mediaplayer.activities.MainActivity;
import com.teno.mediaplayer.R;
import com.teno.mediaplayer.adapter.GenreAdapter;

import java.util.List;

/**
 * Created by Asus on 5/17/2017.
 */

public class FragmentGenre extends Fragment implements GenreAdapter.IGenreAdapter {

    private GenreAdapter mAdapter;
    private RecyclerView mRcvGenre;
    private List<ItemGenre> mListGenre;
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
        mListGenre = ((App)getContext().getApplicationContext()).getListGenre();
        findViewByIds(view);
        showListArtist();
    }

    private void findViewByIds(View view) {
        mRcvGenre = (RecyclerView)view.findViewById(R.id.rcv_list);
        mTv = (TextView)view.findViewById(R.id.tv_list_in_category);
    }

    private void showListArtist() {
        mTv.setText("Tất cả thể loại");
        mAdapter = new GenreAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRcvGenre.setLayoutManager(manager);
        mRcvGenre.setAdapter(mAdapter);
    }

    @Override
    public int getCount() {
        if (mListGenre == null) {
            return 0;
        }
        return mListGenre.size();
    }

    @Override
    public ItemGenre getItem(int position) {
        return mListGenre.get(position);
    }

    @Override
    public void onItemClick(int position) {
        ItemGenre genre = mListGenre.get(position);
        MainActivity main = (MainActivity)getActivity();
        main.showFragmentListSong("Genre", genre.getId());
    }
}
