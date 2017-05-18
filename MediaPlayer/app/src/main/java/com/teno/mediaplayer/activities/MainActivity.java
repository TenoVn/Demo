package com.teno.mediaplayer.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.teno.mediaplayer.R;
import com.teno.mediaplayer.fragment.FragmentHomeOffline;
import com.teno.mediaplayer.fragment.FragmentListenSong;
import com.teno.mediaplayer.fragment.FragmentSong;
import com.teno.mediaplayer.interf.IGetService;

public class MainActivity extends AppCompatActivity {

    private FragmentHomeOffline mFragmentHome;
    private FragmentSong mFragmentSong;
    private FragmentListenSong mFragmentListenSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
        addFragmentHome();
    }



    private void initFragment() {
        mFragmentHome = new FragmentHomeOffline();
    }

    private void addFragmentHome() {
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, mFragmentHome).commit();
    }

    public void showFragmentListSong(String category, int id){
        mFragmentSong = new FragmentSong();
        Bundle bundle = new Bundle();
        bundle.putString("Category", category);
        bundle.putInt("Id", id);
        mFragmentSong.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.enter_right_to_left,
                        R.anim.exit_right_to_left,
                        R.anim.enter_left_to_right,
                        R.anim.exit_left_to_right)
                .replace(R.id.fl_content, mFragmentSong)
                .addToBackStack("List category")
                .commit();
    }

    public void showFragmentListenSong(IGetService iGetService){
        mFragmentListenSong = new FragmentListenSong();
        mFragmentListenSong.setServiceMedia(iGetService);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.enter_right_to_left,
                        R.anim.exit_right_to_left,
                        R.anim.enter_left_to_right,
                        R.anim.exit_left_to_right)
                .replace(R.id.fl_content, mFragmentListenSong)
                .addToBackStack("List category")
                .commit();
    }
}
