package com.teno.mediaplayer.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.teno.mediaplayer.interf.IGetService;
import com.teno.mediaplayer.activities.MainActivity;
import com.teno.mediaplayer.R;
import com.teno.mediaplayer.anima.RotateAnimationPauseAble;
import com.teno.mediaplayer.service.ServiceMedia;

import java.text.SimpleDateFormat;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Asus on 5/17/2017.
 */

public class FragmentListenSong extends Fragment implements View.OnClickListener {

    private CircleImageView mImgDisc;
    private RotateAnimationPauseAble mAnimation;
    private ImageView mBtnPlayStop;
    private TextView mTvCurrentTime;
    private TextView mTvRemainingTime;
    private TextView mTvSongName;
    private SeekBar mSeekBar;
    private int mCurrentTime, mRemainingTime, mCurrentPosition;
    private Runnable mRunnable;
    private ServiceMedia mServiceMedia;
    private Handler mHandler;
    private SimpleDateFormat mFormat;
    private IGetService mIGetService;
    private boolean isRunningDisc;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getService();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listen_song, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewByIds(view);
        setEvents(view);
        initAnimation();
        getService();
        setData();
        mHandler = new Handler();
        playSong();
    }

    private void getService() {
        mServiceMedia = mIGetService.getService();
    }

    public void setServiceMedia(IGetService iGetService) {
        mIGetService = iGetService;
    }

    private void findViewByIds(View view) {
        mImgDisc = (CircleImageView)view.findViewById(R.id.iv_disc);
        mTvSongName = (TextView)view.findViewById(R.id.tv_song_name);
        mBtnPlayStop = (ImageView) view.findViewById(R.id.btn_play_stop);
        mTvCurrentTime = (TextView)view.findViewById(R.id.tv_currentTime);
        mTvRemainingTime = (TextView)view.findViewById(R.id.tv_remainingTime);
        mSeekBar = (SeekBar)view.findViewById(R.id.seek_bar);
    }

    private void setEvents(View view){
        mBtnPlayStop.setOnClickListener(this);
        view.findViewById(R.id.btn_next).setOnClickListener(this);
        view.findViewById(R.id.btn_previous).setOnClickListener(this);
        view.findViewById(R.id.btn_back).setOnClickListener(this);
    }

    private void initAnimation(){
        mAnimation = new RotateAnimationPauseAble(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mAnimation.setDuration(3000);
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setFillAfter(true);
        mImgDisc.setAnimation(mAnimation);
    }

    private void setData() {
        mTvSongName.setText(mServiceMedia.getNameOfSong());
    }


    private void playSong() {
        mCurrentTime = mServiceMedia.getCurrentTime();
        mRemainingTime = mServiceMedia.getDuration();
        mCurrentPosition = mServiceMedia.getCurrentPosition();
        if(mServiceMedia.getImageAlbumArt(mCurrentPosition)!=null){
            Bitmap bitmap = BitmapFactory.decodeFile(mServiceMedia.getImageAlbumArt(mCurrentPosition));
            mImgDisc.setImageBitmap(bitmap);
        }
        else
            mImgDisc.setImageResource(R.drawable.disc);

        mFormat = new SimpleDateFormat("mm:ss");

        mTvCurrentTime.setText(mFormat.format(mCurrentTime));
        mTvRemainingTime.setText(mFormat.format(mRemainingTime));
        mSeekBar.setProgress(mCurrentTime);
        mSeekBar.setMax(mRemainingTime);
        updatePosition();
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                isRunningDisc = true;
                if(fromUser){
                    mServiceMedia.seekTo(progress);
                }
                if(progress == mServiceMedia.getDuration()){
                    mServiceMedia.nextSong();
                    setData();
                    playSong();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void updatePosition(){
        if(isRunningDisc){
            mAnimation.resume();
            isRunningDisc = false;
        }
        mBtnPlayStop.setImageResource(R.drawable.apollo_holo_dark_pause);
        int time = mServiceMedia.getDuration() - mServiceMedia.getCurrentTime();
        if(mServiceMedia.getCurrentTime()==mServiceMedia.getDuration()-1000){
            time = 0;
        }
        mSeekBar.setProgress(mServiceMedia.getCurrentTime());
        mTvCurrentTime.setText(mFormat.format(mServiceMedia.getCurrentTime()));
        mTvRemainingTime.setText(mFormat.format(time));
        if(mServiceMedia.isPlaying()){
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    updatePosition();
                }
            };
        }
        else{
            mBtnPlayStop.setImageResource(R.drawable.apollo_holo_dark_play);
            mAnimation.pause();
            isRunningDisc = false;
        }
        mHandler.postDelayed(mRunnable, 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                backToList();
                break;
            case R.id.btn_play_stop:
                if(mServiceMedia.isPlaying()){
                    mAnimation.pause();
                    mBtnPlayStop.setImageResource(R.drawable.apollo_holo_dark_play);
                    mServiceMedia.playSong();
                }
                else{
                    mAnimation.resume();
                    mBtnPlayStop.setImageResource(R.drawable.apollo_holo_dark_pause);
                    mServiceMedia.playSong();
                }
                break;
            case R.id.btn_previous:
                mBtnPlayStop.setImageResource(R.drawable.apollo_holo_dark_pause);
                mAnimation.resume();
                mServiceMedia.prevSong();
                setData();
                playSong();
                break;
            case R.id.btn_next:
                mBtnPlayStop.setImageResource(R.drawable.apollo_holo_dark_pause);
                mAnimation.resume();
                mServiceMedia.nextSong();
                setData();
                playSong();
                break;
            default:
                break;
        }
    }

    private void backToList() {
        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onDestroyView() {
        mHandler.removeCallbacks(mRunnable);
        super.onDestroyView();
    }
}

