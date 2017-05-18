package com.teno.mediaplayer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teno.mediaplayer.interf.IGetPosition;
import com.teno.mediaplayer.item.ItemArtist;
import com.teno.mediaplayer.R;

/**
 * Created by Asus on 5/12/2017.
 */

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ItemArtistViewHolder>
        implements View.OnClickListener{

    private IArtistAdapter mIArtistAdapter;

    public ArtistAdapter(IArtistAdapter iArtistAdapter) {
        mIArtistAdapter = iArtistAdapter;
    }

    @Override
    public ItemArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View contentView = inflater.inflate(R.layout.item_artist, parent, false);
        return new ItemArtistViewHolder(contentView, this);
    }

    @Override
    public void onBindViewHolder(ItemArtistViewHolder holder, int position) {
        ItemArtist artist = mIArtistAdapter.getItem(position);
        holder.tvNameArtist.setText(artist.getArtist());
        holder.tvNumberOfAlbum.setText("Ablum: "+artist.getNumberOfAlbum());
        holder.tvNumberOfTrack.setText("Bài hát: "+artist.getNumberOfTrack());
    }

    @Override
    public int getItemCount() {
        return mIArtistAdapter.getCount();
    }

    @Override
    public void onClick(View v) {
        IGetPosition iGetPosition = (IGetPosition)v.getTag();
        mIArtistAdapter.onItemClick(iGetPosition.getPosition());
    }

    public interface IArtistAdapter{
        int getCount();
        ItemArtist getItem(int position);
        void onItemClick(int position);
    }

    static class ItemArtistViewHolder extends RecyclerView.ViewHolder{

        private TextView tvNameArtist;
        private TextView tvNumberOfAlbum;
        private TextView tvNumberOfTrack;

        public ItemArtistViewHolder(View itemView, View.OnClickListener onClickListener) {
            super(itemView);
            tvNameArtist = (TextView)itemView.findViewById(R.id.tv_artist_name);
            tvNumberOfAlbum = (TextView)itemView.findViewById(R.id.tv_number_of_album);
            tvNumberOfTrack = (TextView)itemView.findViewById(R.id.tv_number_of_track);
            itemView.setOnClickListener(onClickListener);
            IGetPosition iGetPosition = new IGetPosition() {
                @Override
                public int getPosition() {
                    return getAdapterPosition();
                }
            };
            itemView.setTag(iGetPosition);
        }
    }
}
