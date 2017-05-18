package com.teno.mediaplayer.item;

/**
 * Created by Asus on 5/12/2017.
 */

public class ItemArtist {
    private int mId;
    private String mArtist;
    private int mNumberOfAlbum;
    private int mNumberOfTrack;

    public ItemArtist(int id, String artist, int numberOfAlbum, int numberOfTrack) {
        mId = id;
        mArtist = artist;
        mNumberOfAlbum = numberOfAlbum;
        mNumberOfTrack = numberOfTrack;
    }

    public int getId() {
        return mId;
    }

    public String getArtist() {
        return mArtist;
    }

    public int getNumberOfAlbum() {
        return mNumberOfAlbum;
    }

    public int getNumberOfTrack() {
        return mNumberOfTrack;
    }
}
