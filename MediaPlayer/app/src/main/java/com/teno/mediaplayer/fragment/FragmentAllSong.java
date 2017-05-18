package com.teno.mediaplayer.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.teno.mediaplayer.interf.IGetService;
import com.teno.mediaplayer.item.ItemSong;
import com.teno.mediaplayer.activities.MainActivity;
import com.teno.mediaplayer.R;
import com.teno.mediaplayer.service.ServiceMedia;
import com.teno.mediaplayer.adapter.SongAdapter;

import java.io.IOException;

/**
 * Created by Asus on 5/7/2017.
 */

public class FragmentAllSong extends Fragment implements SongAdapter.ISongAdapter{

    private SongAdapter mAdapter;
    private RecyclerView mRcvAllSong;
    private TextView mTv;
    private ServiceConnection mConnection;
    private ServiceMedia mServiceMedia;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fagment_category, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewByIds(view);
        startService();
        showListAlbum();
        requestService();
    }

    private void startService() {
        Intent intent = new Intent();
        intent.setClass(getContext(), ServiceMedia.class);
        intent.putExtra("List song request", "All song");
        getContext().startService(intent);
    }

    private void requestService() {
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                ServiceMedia.BinderService binderService = (ServiceMedia.BinderService)service;
                mServiceMedia = binderService.getServiceMedia();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        Intent intent = new Intent();
        intent.setClass(getContext(), ServiceMedia.class);
        getContext().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private void findViewByIds(View view) {
        mRcvAllSong = (RecyclerView)view.findViewById(R.id.rcv_list);
        mTv = (TextView)view.findViewById(R.id.tv_list_in_category);
    }

    private void showListAlbum() {
        mTv.setText("Tất cả bài hát");
        mAdapter = new SongAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRcvAllSong.setLayoutManager(manager);
        mRcvAllSong.setAdapter(mAdapter);
    }

    @Override
    public int getCount() {
        if (mServiceMedia == null) {
            return 0;
        }
        return mServiceMedia.getCount();
    }

    @Override
    public ItemSong getItem(int position) {
        return mServiceMedia.getItemSong(position);
    }

    @Override
    public void onItemCLick(int position) {
        try {
            mServiceMedia.play(position);
        } catch (IOException e) {
            Toast.makeText(getContext(), "Không thể mở bài hát này", Toast.LENGTH_SHORT);
            e.printStackTrace();
        }
        IGetService iGetService = new IGetService() {
            @Override
            public ServiceMedia getService() {
                return mServiceMedia;
            }
        };
        MainActivity main = (MainActivity)getActivity();
        main.showFragmentListenSong(iGetService);
    }

    @Override
    public void onDestroyView() {
        getContext().unbindService(mConnection);
        super.onDestroyView();
    }
}

