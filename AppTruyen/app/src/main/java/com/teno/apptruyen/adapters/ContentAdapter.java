package com.teno.apptruyen.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teno.apptruyen.item.ItemDataStory;
import com.teno.apptruyen.R;

/**
 * Created by Asus on 5/2/2017.
 */

public class ContentAdapter extends PagerAdapter {

    private IContentAdapter mIContentAdapter;

    public ContentAdapter(IContentAdapter iContentAdapter) {
        mIContentAdapter = iContentAdapter;
    }

    @Override
    public int getCount() {
        return mIContentAdapter.getCount();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ItemDataStory itemDataStory = mIContentAdapter.getItem(position);
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View contentView = inflater.inflate(R.layout.item_content_story, container, false);
        TextView tvTitle = (TextView)contentView.findViewById(R.id.tv_title_story);
        TextView tvContent = (TextView)contentView.findViewById(R.id.tv_content_story);
        tvTitle.setText(itemDataStory.getNameStory());
        tvContent.setText(itemDataStory.getContentStory());
        contentView.setTag(position);
        container.addView(contentView);
        return contentView;
    }

    public interface IContentAdapter {
        int getCount();
        ItemDataStory getItem(int position);
    }
}
