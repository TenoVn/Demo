package com.teno.apptruyen.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teno.apptruyen.item.ItemDataStory;
import com.teno.apptruyen.R;

/**
 * Created by Asus on 4/24/2017.
 */

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ItemStoryViewHolder>
        implements View.OnClickListener{

    private IStoryAdapter mIStoryAdapter;

    public StoryAdapter(IStoryAdapter iStoryAdapter) {
        mIStoryAdapter = iStoryAdapter;
    }

    @Override
    public ItemStoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View convertView = inflater.inflate(R.layout.item_story, parent, false);
        return new ItemStoryViewHolder(convertView, this);
    }

    @Override
    public void onBindViewHolder(ItemStoryViewHolder holder, int position) {
        ItemDataStory dataStory = mIStoryAdapter.getStoryData(position);
        holder.tv.setText(dataStory.getNameStory());
    }

    @Override
    public int getItemCount() {
        return mIStoryAdapter.getCount();
    }

    @Override
    public void onClick(View v) {
        IGetPosition getPosition = (IGetPosition)v.getTag();
        mIStoryAdapter.onItemClick(getPosition.getPositionTagData());
    }

    public interface IStoryAdapter{
        int getCount();
        ItemDataStory getStoryData(int position);
        void onItemClick(int position);
    }

    static class ItemStoryViewHolder extends RecyclerView.ViewHolder{

        private TextView tv;

        public ItemStoryViewHolder(View itemView, View.OnClickListener onClickListener) {
            super(itemView);
            tv = (TextView)itemView.findViewById(R.id.tv_story);
            itemView.setOnClickListener(onClickListener);
            IGetPosition iGetPosition = new IGetPosition() {
                @Override
                public int getPositionTagData() {
                    return getAdapterPosition();
                }
            };
            itemView.setTag(iGetPosition);
        }
    }
}
