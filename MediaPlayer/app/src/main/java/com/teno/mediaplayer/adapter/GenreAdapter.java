package com.teno.mediaplayer.adapter;

/**
 * Created by Asus on 5/17/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teno.mediaplayer.interf.IGetPosition;
import com.teno.mediaplayer.item.ItemGenre;
import com.teno.mediaplayer.R;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ItemGenreViewHolder>
        implements View.OnClickListener{

    private IGenreAdapter mIGenreAdapter;

    public GenreAdapter(IGenreAdapter iGenreAdapter) {
        mIGenreAdapter = iGenreAdapter;
    }

    @Override
    public ItemGenreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View contentView = inflater.inflate(R.layout.item_genre, parent, false);
        return new ItemGenreViewHolder(contentView, this);
    }

    @Override
    public void onBindViewHolder(ItemGenreViewHolder holder, int position) {
        ItemGenre genre = mIGenreAdapter.getItem(position);
        holder.tvNameGenre.setText(genre.getName());
        holder.tvNumberOfTrack.setText(genre.getCount()+ " bài hát");
        holder.tvDurationTotal.setText(genre.getDuration());
    }

    @Override
    public int getItemCount() {
        return mIGenreAdapter.getCount();
    }

    @Override
    public void onClick(View v) {
        IGetPosition iGetPosition = (IGetPosition)v.getTag();
        mIGenreAdapter.onItemClick(iGetPosition.getPosition());
    }

    public interface IGenreAdapter {
        int getCount();
        ItemGenre getItem(int position);
        void onItemClick(int position);
    }

    static class ItemGenreViewHolder extends RecyclerView.ViewHolder{

        private TextView tvNameGenre;
        private TextView tvNumberOfTrack;
        private TextView tvDurationTotal;

        public ItemGenreViewHolder(View itemView, View.OnClickListener onClickListener) {
            super(itemView);
            tvNameGenre = (TextView)itemView.findViewById(R.id.tv_genre_name);
            tvNumberOfTrack = (TextView)itemView.findViewById(R.id.tv_number_of_track);
            tvDurationTotal = (TextView)itemView.findViewById(R.id.tv_duration_total);
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
