package com.teno.mediaplayer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teno.mediaplayer.app.App;
import com.teno.mediaplayer.item.ItemAlbum;
import com.teno.mediaplayer.activities.MainActivity;
import com.teno.mediaplayer.R;
import com.teno.mediaplayer.adapter.AlbumAdapter;

import java.util.List;

/**
 * Created by Asus on 5/11/2017.
 */

public class FragmentAlbum extends Fragment implements AlbumAdapter.IAlbumAdapter {

    private AlbumAdapter mAdapter;
    private RecyclerView mRcvAlbum;
    private List<ItemAlbum> mListAlbum;
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
        mListAlbum = ((App)getContext().getApplicationContext()).getListAlbum();
        findViewByIds(view);
        showListAlbum();
    }

    private void findViewByIds(View view) {
        mRcvAlbum = (RecyclerView)view.findViewById(R.id.rcv_list);
        mTv = (TextView)view.findViewById(R.id.tv_list_in_category);
    }

    private void showListAlbum() {
        mTv.setText("Tất cả Album");
        mAdapter = new AlbumAdapter(this);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        mRcvAlbum.setLayoutManager(manager);
        mRcvAlbum.setAdapter(mAdapter);
    }

    @Override
    public int getCount() {
        if (mListAlbum == null) {
            return 0;
        }
        return mListAlbum.size();
    }

    @Override
    public ItemAlbum getItem(int position) {
        return mListAlbum.get(position);
    }

    @Override
    public void onItemClick(int position) {
        ItemAlbum album = mListAlbum.get(position);
        MainActivity main = (MainActivity)getActivity();
        main.showFragmentListSong("Album", album.getId());
    }
}
