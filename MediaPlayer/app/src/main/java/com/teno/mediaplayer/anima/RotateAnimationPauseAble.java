package com.teno.mediaplayer.anima;

import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;

/**
 * Created by Asus on 5/17/2017.
 */

public class RotateAnimationPauseAble extends RotateAnimation {

    private long mElapsedAtPause = 0;
    private boolean mPaused = false;

    public RotateAnimationPauseAble(float fromDegrees, float toDegrees, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue) {
        super(fromDegrees, toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue);
    }


    @Override
    public boolean getTransformation(long currentTime, Transformation outTransformation) {
        if(mPaused && mElapsedAtPause==0) {
            mElapsedAtPause=currentTime-getStartTime();
        }
        if(mPaused)
            setStartTime(currentTime-mElapsedAtPause);
        return super.getTransformation(currentTime, outTransformation);
    }

    public void pause() {
        mElapsedAtPause = 0;
        mPaused = true;
    }

    public void resume() {
        mPaused = false;
    }
}
