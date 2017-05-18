package com.teno.ailatrieuphufix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private FragmentMenu fragmentMenu;
    private FragmentPlay fragmentPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
        addFragmentMenu();
    }

    private void initFragment() {
        fragmentMenu = new FragmentMenu();
        fragmentPlay = new FragmentPlay();
    }

    public void addFragmentMenu(){
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, fragmentMenu)
                .addToBackStack("Menu").commit();
    }

    public void showFragmentMenu(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, fragmentMenu)
                .addToBackStack("Menu").commit();
    }

    public void showFragmentPlay(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, fragmentPlay)
                .addToBackStack("Play").commit();
    }
}
