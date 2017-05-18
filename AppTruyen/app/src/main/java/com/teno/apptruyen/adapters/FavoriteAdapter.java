package com.teno.apptruyen.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.teno.apptruyen.item.ItemDataStory;
import com.teno.apptruyen.R;

/**
 * Created by Asus on 4/24/2017.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ItemStoryViewHolder>
        implements View.OnClickListener{

    private IFavoriteAdapter mIFavoriteAdapter;

    public FavoriteAdapter(IFavoriteAdapter iFavoriteAdapter) {
        mIFavoriteAdapter = iFavoriteAdapter;
    }

    @Override
    public ItemStoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View convertView = inflater.inflate(R.layout.item_favorite, parent, false);
        return new ItemStoryViewHolder(convertView, this);
    }

    @Override
    public void onBindViewHolder(ItemStoryViewHolder holder, int position) {
        ItemDataStory dataStory = mIFavoriteAdapter.getStoryData(position);
        holder.tv.setText(dataStory.getNameStory());
    }

    @Override
    public int getItemCount() {
        return mIFavoriteAdapter.getCount();
    }

    @Override
    public void onClick(View v) {
        IGetPosition getPosition = (IGetPosition)v.getTag();
        mIFavoriteAdapter.onItemClick(getPosition.getPositionTagData(), v.getId());
    }

    public interface IFavoriteAdapter{
        int getCount();
        ItemDataStory getStoryData(int position);
        void onItemClick(int position, int id);
    }

    static class ItemStoryViewHolder extends RecyclerView.ViewHolder{

        private TextView tv;
        private ImageView iv;

        public ItemStoryViewHolder(View itemView, View.OnClickListener onClickListener) {
            super(itemView);
            tv = (TextView)itemView.findViewById(R.id.tv_story);
            iv = (ImageView) itemView.findViewById(R.id.iv_delete);
            iv.setOnClickListener(onClickListener);
            tv.setOnClickListener(onClickListener);
            IGetPosition iGetPosition = new IGetPosition() {
                @Override
                public int getPositionTagData() {
                    return getAdapterPosition();
                }
            };
            iv.setTag(iGetPosition);
            tv.setTag(iGetPosition);
        }
    }
}
