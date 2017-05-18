package com.teno.apptruyen.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.teno.apptruyen.item.ItemDataTopic;
import com.teno.apptruyen.R;

/**
 * Created by Asus on 4/24/2017.
 */

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ItemTopicViewHolder>
        implements View.OnClickListener{

    private ITopicAdapter mITopicAdapter;

    public TopicAdapter(ITopicAdapter iTopicAdapter) {
        mITopicAdapter = iTopicAdapter;
    }

    @Override
    public ItemTopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View convertView = inflater.inflate(R.layout.item_topic, parent, false);
        return new ItemTopicViewHolder(convertView, this);
    }

    @Override
    public void onBindViewHolder(ItemTopicViewHolder holder, int position) {
        ItemDataTopic dataTopic = mITopicAdapter.getData(position);
        holder.mTvTopic.setText(dataTopic.getNameTopic());
        holder.mIvTopic.setImageResource(dataTopic.getIdImageTopic());
    }

    @Override
    public int getItemCount() {
        return mITopicAdapter.getCount();
    }

    @Override
    public void onClick(View v) {
        IGetPosition getPosition = (IGetPosition)v.getTag();
        mITopicAdapter.onItemClick(getPosition.getPositionTagData());
    }

    public interface ITopicAdapter{
        int getCount();
        ItemDataTopic getData(int position);
        void onItemClick(int position);
    }

    static class ItemTopicViewHolder extends RecyclerView.ViewHolder{

        private TextView mTvTopic;
        private ImageView mIvTopic;

        public ItemTopicViewHolder(View itemView, View.OnClickListener onClickListener) {
            super(itemView);
            mTvTopic = (TextView)itemView.findViewById(R.id.tv_topic);
            mIvTopic = (ImageView)itemView.findViewById(R.id.iv_topic);
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
