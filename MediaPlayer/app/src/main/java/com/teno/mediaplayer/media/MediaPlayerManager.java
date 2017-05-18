package com.teno.mediaplayer.media;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by Asus on 5/17/2017.
 */

public class MediaPlayerManager implements MediaPlayer.OnCompletionListener {
    private MediaPlayer mPlay;

    public MediaPlayerManager() {
        mPlay = new MediaPlayer();
    }

    public void initAudioOffline(String path) throws IOException {
        try {
            mPlay.setDataSource(path);
            mPlay.setOnCompletionListener(this);
        }catch (IllegalStateException e) {
            mPlay = new MediaPlayer();
            mPlay.setDataSource(path);
            mPlay.setOnCompletionListener(this);
        }

        mPlay.prepare();
    }

    public void release() {
        mPlay.release();
    }

    public void play() {
        if (!mPlay.isPlaying()) {
            mPlay.start();
        }
    }

    public void pause() {
        if (mPlay.isPlaying()) {
            mPlay.pause();
        }
    }

    public boolean isPlaying() {
        return mPlay.isPlaying();
    }

    public void stop() {
        mPlay.stop();
    }

    public int getCurrentTime(){
        return mPlay.getCurrentPosition();
    }

    public int getDuration(){
        int duration;
        try{
            duration = mPlay.getDuration();
        }
        catch (IllegalStateException e){
            return 0;
        }
        return duration;
    }

    public void seekTo(int process){
        mPlay.seekTo(process);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }
}
