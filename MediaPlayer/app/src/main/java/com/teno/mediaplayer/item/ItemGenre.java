package com.teno.mediaplayer.item;

/**
 * Created by Asus on 5/17/2017.
 */

public class ItemGenre {
    private int mId;
    private String mName;
    private int mCount;
    private String mDuration;

    public ItemGenre(int id, String name, int count, String duration) {
        mId = id;
        mName = name;
        mCount = count;
        mDuration = duration;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public int getCount() {
        return mCount;
    }

    public String getDuration() {
        return mDuration;
    }
}
