package com.teno.mediaplayer.app;

import android.app.Application;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.teno.mediaplayer.item.ItemAlbum;
import com.teno.mediaplayer.item.ItemArtist;
import com.teno.mediaplayer.item.ItemGenre;
import com.teno.mediaplayer.item.ItemSong;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 5/7/2017.
 */

public class App extends Application {

    private static final String TAG = App.class.getSimpleName();
    private List<String> mListTabLayoutMenu;
    private List<ItemArtist> mListArtist;
    private List<ItemAlbum> mListAlbum;
    private List<ItemGenre> mListGenre;
    private List<ItemSong> mListAllSong;

    @Override
    public void onCreate() {
        super.onCreate();
        initList();
        initListTabLayoutMenu();
        mListArtist = getAllArtist();
        mListAlbum = getAllAlbum();
        mListGenre = getAllGenre();
        mListAllSong = getAllSong();
    }

    public List<ItemArtist> getListArtist() {
        return mListArtist;
    }

    public List<ItemAlbum> getListAlbum() {
        return mListAlbum;
    }

    public List<ItemGenre> getListGenre() {
        return mListGenre;
    }

    public List<ItemSong> getListAllSong() {
        return mListAllSong;
    }

    private void initList(){
        mListTabLayoutMenu = new ArrayList<>();
        mListArtist = new ArrayList<>();
        mListAlbum = new ArrayList<>();
        mListGenre = new ArrayList<>();
    }

    private void initListTabLayoutMenu(){
        mListTabLayoutMenu.add("TẤT CẢ BÀI HÁT");
        mListTabLayoutMenu.add("ALBUM");
        mListTabLayoutMenu.add("NGHỆ SĨ");
        mListTabLayoutMenu.add("THỂ LOẠI");
    }

    public List<String> getListTabLayoutMenu() {
        return mListTabLayoutMenu;
    }

    public List<ItemAlbum> getAllAlbum(){
        List<ItemAlbum> list = new ArrayList<>();
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String projection[] = {
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.ARTIST,
                MediaStore.Audio.Albums.ALBUM_ART,
                MediaStore.Audio.Albums.NUMBER_OF_SONGS};

        String selection = null;
        String sortOrder = MediaStore.Audio.Albums.ALBUM + " COLLATE LOCALIZED ASC";

        Cursor cursor = getContentResolver().query(uri, projection, selection, null, sortOrder);

        if(cursor == null){
            return null;
        }

        int indexId = cursor.getColumnIndex(MediaStore.Audio.Albums._ID);
        int indexAlbum = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
        int indexArtist = cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST);
        int indexArt = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
        int indexNumberOfSongs = cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int id = cursor.getInt(indexId);
            String album = cursor.getString(indexAlbum);
            String artist = cursor.getString(indexArtist);
            String art = cursor.getString(indexArt);
            int numberOfSongs = cursor.getInt(indexNumberOfSongs);

            ItemAlbum albumObj = new ItemAlbum(id, album, artist, art, numberOfSongs);
            list.add(albumObj);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<ItemArtist> getAllArtist(){
        List<ItemArtist> list = new ArrayList<>();
        Uri uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
        String projection[] = {
                MediaStore.Audio.Artists._ID,
                MediaStore.Audio.Artists.ARTIST,
                MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
                MediaStore.Audio.Artists.NUMBER_OF_TRACKS};

        String selection = null;
        String sortOrder = MediaStore.Audio.Artists.ARTIST + " COLLATE LOCALIZED ASC";

        Cursor cursor = getContentResolver().query(uri, projection, selection, null, sortOrder);

        if(cursor == null){
            return null;
        }

        int indexId = cursor.getColumnIndex(MediaStore.Audio.Artists._ID);
        int indexArtist = cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST);
        int indexNumberOfAlbum = cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS);
        int indexNumberOfTracks = cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int id = cursor.getInt(indexId);
            String artistName = cursor.getString(indexArtist);
            int numberOfAlbum = cursor.getInt(indexNumberOfAlbum);
            int numberOfTracks = cursor.getInt(indexNumberOfTracks);

            ItemArtist artist = new ItemArtist(id, artistName, numberOfAlbum, numberOfTracks);
            list.add(artist);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<ItemGenre> getAllGenre(){
        List<ItemGenre> list = new ArrayList<>();
        Uri uri = MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI;
        String projection[] = {
                MediaStore.Audio.Genres._ID,
                MediaStore.Audio.Genres.NAME};

        String selection = null;
        String sortOrder = MediaStore.Audio.Genres.NAME + " COLLATE LOCALIZED ASC";

        Cursor cursor = getContentResolver().query(uri, projection, selection, null, sortOrder);

        if(cursor == null){
            return null;
        }

        int indexId = cursor.getColumnIndex(MediaStore.Audio.Genres._ID);
        int indexName = cursor.getColumnIndex(MediaStore.Audio.Genres.NAME);


        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int id = cursor.getInt(indexId);
            String genreName = cursor.getString(indexName);
            int count = getNumberOfSongByGenre(id);
            String durationTotal = getDurationOfListSongByGenre(id);

            ItemGenre genre = new ItemGenre(id, genreName, count, durationTotal);
            Log.d(TAG, "ID: " + id);
            Log.d(TAG, "NAME: " + genreName);
            Log.d(TAG, "COUNT: " + count);
            Log.d(TAG, "TOTAL: " + durationTotal);
            list.add(genre);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<ItemSong> getAllSong(){
        List<ItemSong> listSong = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String projection[] = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID};
        String selection = "is_music != 0";
        String sortOrder = MediaStore.Audio.AudioColumns.TITLE + " COLLATE LOCALIZED ASC";

        Cursor cursor = getContentResolver().query(uri, projection, selection, null, sortOrder);

        if(cursor == null){
            return null;
        }

        int indexID = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
        int indexTitle = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int indexArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int indexData = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        int indexDisplayName = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
        int indexDuration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
        int indexAlbum = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
        int indexAlbumId = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int id = cursor.getInt(indexID);
            String title = cursor.getString(indexTitle);
            String artist = cursor.getString(indexArtist);
            String data = cursor.getString(indexData);
            String displayName = cursor.getString(indexDisplayName);
            int duration = cursor.getInt(indexDuration);
            String album = cursor.getString(indexAlbum);
            int albumId = cursor.getInt(indexAlbumId);
            String albumArt = getAlbumArt(albumId);
            ItemSong song = new ItemSong(id, title, artist, data, displayName, duration, album, albumId, albumArt);
            listSong.add(song);
            cursor.moveToNext();
        }
        cursor.close();
        return listSong;
    }

    private String getAlbumArt(int albumId) {
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String projection[] = {MediaStore.Audio.Albums.ALBUM_ART};
        String selection = MediaStore.Audio.Albums._ID + " = " + albumId;

        Cursor cursor = getContentResolver().query(uri, projection, selection, null, null);

        if(cursor == null){
            return null;
        }
        cursor.moveToFirst();
        String albumArt = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
        cursor.close();
        return albumArt;
    }

    public int getNumberOfSongByGenre(int genreId){
        int count = 0;
        Uri uri = MediaStore.Audio.Genres.Members.getContentUri("external", genreId);
        String projection[] = {
                MediaStore.Audio.Genres.Members.TITLE};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if(cursor == null){
            return 0;
        }
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            count = count + 1;
            cursor.moveToNext();
        }
        cursor.close();
        return count;
    }

    public String getDurationOfListSongByGenre(int genreId){
        long duration = 0;
        String durationTotal;
        Uri uri = MediaStore.Audio.Genres.Members.getContentUri("external", genreId);
        String projection[] = {
                MediaStore.Audio.Genres.Members.DURATION};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if(cursor == null){
            return null;
        }
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            long time = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Genres.Members.DURATION));
            duration = duration + time;
            cursor.moveToNext();
        }
        cursor.close();
        SimpleDateFormat format1 = new SimpleDateFormat("mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("hh:mm:ss");
        if(duration>=3600000){
            durationTotal = format2.format(duration);
        }
        else {
            durationTotal = format1.format(duration);
        }
        return durationTotal;
    }
}
