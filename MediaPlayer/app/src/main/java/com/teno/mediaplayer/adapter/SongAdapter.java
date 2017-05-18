package com.teno.mediaplayer.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.teno.mediaplayer.interf.IGetPosition;
import com.teno.mediaplayer.item.ItemSong;
import com.teno.mediaplayer.R;

/**
 * Created by Asus on 5/11/2017.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ItemSongViewHolder> implements View.OnClickListener {

    private ISongAdapter mISongAdapter;

    public SongAdapter(ISongAdapter iSongAdapter) {
        mISongAdapter = iSongAdapter;
    }

    @Override
    public ItemSongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View contentView = inflater.inflate(R.layout.item_song, parent, false);
        return new ItemSongViewHolder(contentView, this);
    }

    @Override
    public void onBindViewHolder(ItemSongViewHolder holder, int position) {
        ItemSong song = mISongAdapter.getItem(position);
        holder.tvNameSong.setText(song.getTitle());
        holder.tvArtist.setText(song.getArtist());
        if(song.getAlbumArt()!=null){
            Bitmap bitmap = BitmapFactory.decodeFile(song.getAlbumArt());
            holder.ivSong.setImageBitmap(bitmap);
        }
        else holder.ivSong.setImageResource(R.drawable.default_music_image);
    }

    @Override
    public int getItemCount() {
        return mISongAdapter.getCount();
    }

    @Override
    public void onClick(View v) {
        IGetPosition iGetPosition = (IGetPosition)v.getTag();
        mISongAdapter.onItemCLick(iGetPosition.getPosition());
    }

    public interface ISongAdapter{
        int getCount();
        ItemSong getItem(int position);
        void onItemCLick(int position);
    }

    static class ItemSongViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivSong;
        private TextView tvNameSong;
        private TextView tvArtist;

        public ItemSongViewHolder(View itemView, View.OnClickListener onClickListener) {
            super(itemView);
            ivSong = (ImageView)itemView.findViewById(R.id.iv_song);
            tvNameSong = (TextView)itemView.findViewById(R.id.tv_song_name);
            tvArtist = (TextView)itemView.findViewById(R.id.tv_song_artist);
            IGetPosition iGetPosition = new IGetPosition() {
                @Override
                public int getPosition() {
                    return getAdapterPosition();
                }
            };
            itemView.setOnClickListener(onClickListener);
            itemView.setTag(iGetPosition);
        }
    }
}
