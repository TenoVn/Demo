package com.teno.mediaplayer.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.teno.mediaplayer.R;
import com.teno.mediaplayer.app.App;
import com.teno.mediaplayer.item.ItemSong;
import com.teno.mediaplayer.media.MediaPlayerManager;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 5/17/2017.
 */

public class ServiceMedia extends Service implements Serializable{

    private static final String TAG = ServiceMedia.class.getSimpleName();
    private MyBroadCast mBroadCast;
    private MediaPlayerManager mMedia;
    private List<ItemSong> mListSong;
    private int mCurrentPosition = -1;

    @Override
    public void onCreate() {
        super.onCreate();
        initList();
        initMyBroadCast();
        mMedia = new MediaPlayerManager();
    }

    private void initList() {
        mListSong = new ArrayList<>();
    }

    private void initMyBroadCast(){
        mBroadCast = new MyBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("Play");
        filter.addAction("Collapse");
        filter.addAction("Next");
        filter.addAction("Prev");
        registerReceiver(mBroadCast, filter);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new BinderService(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String requestList = intent.getStringExtra("List song request");
        int id = intent.getIntExtra("Id", 0);
        if(requestList.equals("All song")){
            mListSong = ((App)getBaseContext().getApplicationContext()).getListAllSong();
        }
        else if(requestList.equals("Album")){
            mListSong = getAllSongInAlbums(id);
        }
        else if(requestList.equals("Artist")){
            mListSong = getAllSongByArtist(id);
        }
        else if(requestList.equals("Genre")){
            mListSong = getAllSongByGenre(id);
        }
        return START_NOT_STICKY;
    }

    private class MyBroadCast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                case "Play":
                    playSong();
                    break;
                case "Collapse":
                    createNotification(mCurrentPosition, true, false, true);
                    break;
                case "Next":
                    nextSong();
                    break;
                case "Prev":
                    prevSong();
                    break;
                default:
                    break;
            }
        }
    }

    public void playSong() {
        if ( mMedia.isPlaying() ) {
            mMedia.pause();
            createNotification(mCurrentPosition, false, false, false);
            Toast.makeText(getBaseContext(), "Pause song", Toast.LENGTH_SHORT).show();
        }else {
            mMedia.play();
            createNotification(mCurrentPosition, true, false, false);
            Toast.makeText(getBaseContext(), "Play song", Toast.LENGTH_SHORT).show();
        }
    }

    public void prevSong() {
        try {
            if(mCurrentPosition >=1){
                mCurrentPosition = mCurrentPosition - 1;
            }
            else
                mCurrentPosition = mListSong.size()-1;
            play(mCurrentPosition);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getBaseContext(), "Prev song", Toast.LENGTH_SHORT).show();
    }

    public void nextSong() {
        try {
            if(mCurrentPosition <mListSong.size()-1){
                mCurrentPosition = mCurrentPosition + 1;
                play(mCurrentPosition);
            }
            else
                play(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getBaseContext(), "Next song", Toast.LENGTH_SHORT).show();
    }

    public boolean isPlaying(){
        return mMedia.isPlaying();
    }

    public static class BinderService extends Binder{
        private ServiceMedia mServiceMedia;

        public BinderService(ServiceMedia serviceMedia) {
            mServiceMedia = serviceMedia;
        }

        public ServiceMedia getServiceMedia() {
            return mServiceMedia;
        }
    }

    private void createNotification(int position, boolean isPlay, boolean checkAlbumArt, boolean isCollapse){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.music_icon);

        RemoteViews bigRemoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification_expanded);
        bigRemoteViews.setTextViewText(R.id.tv_track_name, mListSong.get(position).getTitle());
        bigRemoteViews.setTextViewText(R.id.tv_artist_name, mListSong.get(position).getArtist());
        bigRemoteViews.setTextViewText(R.id.tv_album_name, mListSong.get(position).getAlbum());

        RemoteViews smallRemoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);
        smallRemoteViews.setTextViewText(R.id.tv_track_name, mListSong.get(position).getTitle());
        if(checkAlbumArt){
            if(mListSong.get(position).getAlbumArt()!=null){
                Bitmap bitmap = BitmapFactory.decodeFile(mListSong.get(position).getAlbumArt());
                bigRemoteViews.setImageViewBitmap(R.id.iv_album_art, bitmap);
                smallRemoteViews.setImageViewBitmap(R.id.status_bar_album_art, bitmap);
            }
            else {
                bigRemoteViews.setImageViewResource(R.id.iv_album_art, R.drawable.default_music_image);
                smallRemoteViews.setImageViewResource(R.id.status_bar_album_art, R.drawable.default_music_image);
            }
        }

        if (isPlay) {
            bigRemoteViews.setImageViewResource(R.id.btn_play, R.drawable.apollo_holo_dark_pause);
            smallRemoteViews.setImageViewResource(R.id.btn_play, R.drawable.apollo_holo_dark_pause);
        } else {
            bigRemoteViews.setImageViewResource(R.id.btn_play, R.drawable.apollo_holo_dark_play);
            smallRemoteViews.setImageViewResource(R.id.btn_play, R.drawable.apollo_holo_dark_play);
        }

        Intent playIntent = new Intent();
        playIntent.setAction("Play");
        PendingIntent pendingPlay = PendingIntent.getBroadcast(this, 0, playIntent, 0);
        bigRemoteViews.setOnClickPendingIntent(R.id.btn_play, pendingPlay);
        smallRemoteViews.setOnClickPendingIntent(R.id.btn_play, pendingPlay);

        Intent shutdownIntent = new Intent();
        shutdownIntent.setAction("Collapse");
        PendingIntent pendingShutdown = PendingIntent.getBroadcast(this, 0, shutdownIntent, 0);
        bigRemoteViews.setOnClickPendingIntent(R.id.btn_collapse, pendingShutdown);
        smallRemoteViews.setOnClickPendingIntent(R.id.btn_collapse, pendingShutdown);

        Intent nextIntent = new Intent();
        nextIntent.setAction("Next");
        PendingIntent pendingNext = PendingIntent.getBroadcast(this, 0, nextIntent, 0);
        bigRemoteViews.setOnClickPendingIntent(R.id.btn_next, pendingNext);
        smallRemoteViews.setOnClickPendingIntent(R.id.btn_next, pendingNext);

        Intent prevIntent = new Intent();
        prevIntent.setAction("Prev");
        PendingIntent pendingPrev = PendingIntent.getBroadcast(this, 0, prevIntent, 0);
        bigRemoteViews.setOnClickPendingIntent(R.id.btn_prev, pendingPrev);
        smallRemoteViews.setOnClickPendingIntent(R.id.btn_prev, pendingPrev);


        builder.setCustomContentView(smallRemoteViews);
        builder.setCustomBigContentView(bigRemoteViews);


        if (isPlay) {
            stopForeground(false);
            builder.setAutoCancel(false);
            startForeground(101, builder.build());
        } else {
            builder.setAutoCancel(true);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancel(101);
            manager.notify(101, builder.build());
        }

        if(isCollapse){
            stopForeground(true);
            stop();
        }

    }

    public int getCurrentPosition(){
        return mCurrentPosition;
    }

    public void play(int position) throws IOException {
        mMedia.release();
        mMedia.initAudioOffline(mListSong.get(position).getData());
        mMedia.play();
        mCurrentPosition = position;
        createNotification(position, true, true, false);
    }

    public void stop(){
        mMedia.stop();
        mMedia.release();
    }

//----------------------------GET SONG------------------------------------//
    public List<ItemSong> getAllSongInAlbums(int albumSelect){
        List<ItemSong> listSongByAlbum = new ArrayList<>();
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
        selection = selection + " and " + MediaStore.Audio.Media.ALBUM_ID + " = " + albumSelect;
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
            listSongByAlbum.add(song);
            cursor.moveToNext();
        }
        cursor.close();
        return listSongByAlbum;
    }

    public List<ItemSong> getAllSongByArtist(int artistSelect){
        List<ItemSong> listSongByArtist = new ArrayList<>();
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
        selection = selection + " and " + MediaStore.Audio.Media.ARTIST_ID + " = " + artistSelect;
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
            listSongByArtist.add(song);
            cursor.moveToNext();
        }
        cursor.close();
        return listSongByArtist;
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

    public List<ItemSong> getAllSongByGenre(int genreId){
        List<ItemSong> listSongByGenre = new ArrayList<>();
        Uri uri = MediaStore.Audio.Genres.Members.getContentUri("external", genreId);
        String projection[] = {
                MediaStore.Audio.Genres.Members._ID,
                MediaStore.Audio.Genres.Members.TITLE,
                MediaStore.Audio.Genres.Members.ARTIST,
                MediaStore.Audio.Genres.Members.DATA,
                MediaStore.Audio.Genres.Members.DISPLAY_NAME,
                MediaStore.Audio.Genres.Members.DURATION,
                MediaStore.Audio.Genres.Members.ALBUM,
                MediaStore.Audio.Genres.Members.ALBUM_ID};

        String selection = null;
        String sortOrder = MediaStore.Audio.Genres.Members.TITLE + " COLLATE LOCALIZED ASC";

        Cursor cursor = getContentResolver().query(uri, projection, selection, null, sortOrder);

        if(cursor == null){
            return null;
        }

        int indexID = cursor.getColumnIndex(MediaStore.Audio.Genres.Members._ID);
        int indexTitle = cursor.getColumnIndex(MediaStore.Audio.Genres.Members.TITLE);
        int indexArtist = cursor.getColumnIndex(MediaStore.Audio.Genres.Members.ARTIST);
        int indexData = cursor.getColumnIndex(MediaStore.Audio.Genres.Members.DATA);
        int indexDisplayName = cursor.getColumnIndex(MediaStore.Audio.Genres.Members.DISPLAY_NAME);
        int indexDuration = cursor.getColumnIndex(MediaStore.Audio.Genres.Members.DURATION);
        int indexAlbum = cursor.getColumnIndex(MediaStore.Audio.Genres.Members.ALBUM);
        int indexAlbumId = cursor.getColumnIndex(MediaStore.Audio.Genres.Members.ALBUM_ID);

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
            listSongByGenre.add(song);
            cursor.moveToNext();
        }
        cursor.close();
        return listSongByGenre;
    }

    public int getCount(){
        if(mListSong == null){
            return 0;
        }
        return mListSong.size();
    }

    public String getNameOfSong(){
        ItemSong itemSong = mListSong.get(mCurrentPosition);
        String name = itemSong.getTitle();
        return name;
    }

    public int getDuration(){
        return mMedia.getDuration();
    }

    public int getCurrentTime(){
        return mMedia.getCurrentTime();
    }

    public String getImageAlbumArt(int position){
        return mListSong.get(position).getAlbumArt();
    }

    public void seekTo(int process){
        mMedia.seekTo(process);
    }

    public ItemSong getItemSong(int position){
        return mListSong.get(position);
    }
}
