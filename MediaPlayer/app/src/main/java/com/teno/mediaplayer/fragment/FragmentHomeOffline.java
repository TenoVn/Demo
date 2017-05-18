package com.teno.mediaplayer.fragment;

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

import com.teno.mediaplayer.app.App;
import com.teno.mediaplayer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 5/7/2017.
 */

public class FragmentHomeOffline extends Fragment {

    private static final int ALL_SONGS = 0;
    private static final int ALBUM = 1;
    private static final int ARTIST = 2;
    private static final int TYPE = 3;

    private TabLayout mTabLayout;
    private FragmentAdapter mAdapter;
    private ViewPager mViewPager;
    private List<String> mListTabLayoutMenu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListTabLayoutMenu = ((App)getContext().getApplicationContext()).getListTabLayoutMenu();
        findViewByIds(view);
        initTabLayout();
    }

    private void findViewByIds(View view) {
        mTabLayout = (TabLayout)view.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager)view.findViewById(R.id.viewPager_home);
    }

    private void initTabLayout(){
        mAdapter = new FragmentAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(),
                R.color.colorSelectedTabIndicator));
        mTabLayout.setDrawingCacheBackgroundColor(ContextCompat.getColor(getContext(),
                R.color.colorTextTabNormal));

        List<TextView> listCustomText = new ArrayList<>();
        TextView customText1 = (TextView)LayoutInflater.from(getContext()).inflate(R.layout.item_tab_layout_menu, null);
        listCustomText.add(customText1);

        TextView customText2 = (TextView)LayoutInflater.from(getContext()).inflate(R.layout.item_tab_layout_menu, null);
        listCustomText.add(customText2);

        TextView customText3 = (TextView)LayoutInflater.from(getContext()).inflate(R.layout.item_tab_layout_menu, null);
        listCustomText.add(customText3);

        TextView customText4 = (TextView)LayoutInflater.from(getContext()).inflate(R.layout.item_tab_layout_menu, null);
        listCustomText.add(customText4);

        for (int i = 0; i<mListTabLayoutMenu.size(); i++){
            listCustomText.get(i).setText(mListTabLayoutMenu.get(i));
            mTabLayout.getTabAt(i).setCustomView(listCustomText.get(i));
        }

        mTabLayout.setTabTextColors(ContextCompat.getColor(getContext(), R.color.colorTextTabNormal),
                ContextCompat.getColor(getContext(), R.color.colorTextTabSelect));
    }

    private static class FragmentAdapter extends FragmentPagerAdapter{

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==ALL_SONGS){
                return new FragmentAllSong();
            }
            else if(position == ALBUM){
                return new FragmentAlbum();
            }
            else if(position == ARTIST)
                return new FragmentArtist();
            else
                return new FragmentGenre();
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
