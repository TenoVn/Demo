package com.teno.ailatrieuphufix;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 4/20/2017.
 */

public class DialogBonus extends Dialog implements DialogInterface.OnDismissListener{

    private String[] mBonus;
    private LinearLayout ll_bonus;
    private boolean mIsDismiss;
    private static final String AUDIO_RULES = "Luật chơi";
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;

    public DialogBonus(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.dialog_bonus);
        setCancelable(true);
        getWindow().getAttributes().gravity = Gravity.RIGHT;
        initBonus();
        mIsDismiss = false;
    }

    private void initBonus() {
        mBonus = ((GameApp)getContext().getApplicationContext()).getBonus();
        ll_bonus = (LinearLayout)findViewById(R.id.ll_row_bonus);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for(int i = 0; i<15; i++){
            TextView tvBonus = (TextView)inflater.inflate(R.layout.item_money, ll_bonus, false);
            tvBonus.setText(mBonus[i]);
            if(i==0||i==5||i==10){
                tvBonus.setTextColor(ContextCompat.getColor(getContext(), R.color.colorMilestones));
                tvBonus.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_active));
            }
            tvBonus.setId(i);
            ll_bonus.addView(tvBonus);
        }
    }
    public void updateDialog(int currentLevel) {
        TextView tv = (TextView)ll_bonus.findViewById(currentLevel);
        if(currentLevel<14){
            TextView tvPrevious = (TextView)ll_bonus.findViewById(currentLevel + 1);
            tvPrevious.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.money_milestone));
        }
        tv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.play_answer_background_true));
    }

    public void createSound(){
        mMediaPlayer = new MediaPlayer();
        mAudioManager = new AudioManager(mMediaPlayer);
        mAudioManager.playSound(getContext(), mAudioManager.getAudio(AUDIO_RULES));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dismiss();
        mIsDismiss = true;
        mAudioManager.stopSound();
        return true;
    }

    @Override
    protected void onStop() {
        mIsDismiss = true;
        mAudioManager.stopSound();
        super.onStop();
    }

    public boolean isDismiss() {
        return mIsDismiss;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        mIsDismiss = true;
        mAudioManager.stopSound();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mIsDismiss = true;
        mAudioManager.stopSound();
    }
}
