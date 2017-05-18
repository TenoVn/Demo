package com.teno.mediaplayer.item;

/**
 * Created by Asus on 5/11/2017.
 */

public class ItemSong {
    private int mId;
    private String mTitle;
    private String mArtist;
    private String mData;
    private String mDisplayName;
    private int mDuration;
    private String mAlbum;
    private int mAlbumId;
    private String mAlbumArt;

    public ItemSong(int id, String title, String artist, String data, String displayName,
                    int duration, String album, int albumId, String albumArt) {
        mId = id;
        mTitle = title;
        mArtist = artist;
        mData = data;
        mDisplayName = displayName;
        mDuration = duration;
        mAlbum = album;
        mAlbumId = albumId;
        mAlbumArt = albumArt;
    }

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getArtist() {
        return mArtist;
    }

    public String getData() {
        return mData;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public int getDuration() {
        return mDuration;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public int getAlbumId() {
        return mAlbumId;
    }

    public String getAlbumArt() {
        return mAlbumArt;
    }
}
