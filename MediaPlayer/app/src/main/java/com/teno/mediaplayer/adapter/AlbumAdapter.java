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
import com.teno.mediaplayer.item.ItemAlbum;
import com.teno.mediaplayer.R;

/**
 * Created by Asus on 5/11/2017.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ItemAlbumViewHolder> implements View.OnClickListener {

    private IAlbumAdapter mIAlbumAdapter;

    public AlbumAdapter(IAlbumAdapter iAlbumAdapter) {
        mIAlbumAdapter = iAlbumAdapter;
    }

    @Override
    public ItemAlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View contentView = inflater.inflate(R.layout.item_album, null);
        return new ItemAlbumViewHolder(contentView, this);
    }

    @Override
    public void onBindViewHolder(ItemAlbumViewHolder holder, int position) {
        ItemAlbum itemAlbum =  mIAlbumAdapter.getItem(position);

        if(itemAlbum.getAlbumArt()!=null){
            Bitmap bitmap = BitmapFactory.decodeFile(itemAlbum.getAlbumArt());
            holder.ivAlbum.setImageBitmap(bitmap);
        }
        else holder.ivAlbum.setImageResource(R.drawable.default_music_image);

        holder.tvName.setText(itemAlbum.getAlbum());
        holder.tvArtist.setText(itemAlbum.getArtist());
    }

    @Override
    public int getItemCount() {
        return mIAlbumAdapter.getCount();
    }

    @Override
    public void onClick(View v) {
        IGetPosition iGetPosition = (IGetPosition)v.getTag();
        mIAlbumAdapter.onItemClick(iGetPosition.getPosition());
    }

    public interface IAlbumAdapter{
        int getCount();
        ItemAlbum getItem(int position);
        void onItemClick(int position);
    }

    static class ItemAlbumViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivAlbum;
        private TextView tvName;
        private TextView tvArtist;

        public ItemAlbumViewHolder(View itemView, View.OnClickListener onClickListener) {
            super(itemView);
            ivAlbum = (ImageView)itemView.findViewById(R.id.iv_album);
            tvName = (TextView)itemView.findViewById(R.id.tv_album_name);
            tvArtist = (TextView)itemView.findViewById(R.id.tv_album_artist);
            IGetPosition iGetPosition = new IGetPosition() {
                @Override
                public int getPosition() {
                    return getAdapterPosition();
                }
            };
            itemView.setTag(iGetPosition);
            itemView.setOnClickListener(onClickListener);
        }
    }
}
