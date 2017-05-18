package com.teno.apptruyen.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teno.apptruyen.R;

/**
 * Created by Asus on 4/24/2017.
 */

public class FragmentHome extends Fragment{

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FragmentAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewByIds(view);
        initTabFragment();
    }

    private void initTabFragment() {
        mAdapter = new FragmentAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.colorTab_layout));
        mTabLayout.setSelectedTabIndicatorHeight(getResources().getDimensionPixelSize(R.dimen.tab_layout_height));

        TextView customTab1 = (TextView)LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        TextView customTab2 = (TextView)LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);

        customTab1.setText("Thể loại");
        customTab1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tab_category, 0, 0, 0);

        customTab2.setText("Ưa thích");
        customTab2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tab_favorite, 0, 0, 0);

        mTabLayout.getTabAt(0).setCustomView(customTab1);
        mTabLayout.getTabAt(1).setCustomView(customTab2);
    }

    private void findViewByIds(View view) {
        mViewPager = (ViewPager)view.findViewById(R.id.vp_list);
        mTabLayout = (TabLayout)view.findViewById(R.id.tab_layout);
    }

    private static class FragmentAdapter extends FragmentPagerAdapter{

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0){
                return new FragmentListTopic();
            }
            else{
                return new FragmentListFavorite();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
