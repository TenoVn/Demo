package com.teno.mediaplayer.item;

/**
 * Created by Asus on 5/11/2017.
 */

public class ItemAlbum {
    private int mId;
    private String mAlbum;
    private String mArtist;
    private String mAlbumArt;
    private int mNumberOfSongs;

    public ItemAlbum(int id, String album, String artist, String albumArt, int numberOfSongs) {
        mId = id;
        mAlbum = album;
        mArtist = artist;
        mAlbumArt = albumArt;
        mNumberOfSongs = numberOfSongs;
    }

    public int getId() {
        return mId;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public String getArtist() {
        return mArtist;
    }

    public String getAlbumArt() {
        return mAlbumArt;
    }

    public int getNumberOfSongs() {
        return mNumberOfSongs;
    }
}
