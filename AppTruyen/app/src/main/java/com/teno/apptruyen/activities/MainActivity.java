package com.teno.apptruyen.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.teno.apptruyen.R;
import com.teno.apptruyen.fragments.FragmentContentStory;
import com.teno.apptruyen.fragments.FragmentHome;
import com.teno.apptruyen.fragments.FragmentListStory;

public class MainActivity extends AppCompatActivity {

    private FragmentHome mFragmentHome;
    private FragmentContentStory mFragmentContentStory;
    private FragmentListStory mFragmentListStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragmentHome();
    }

    private void addFragmentHome(){
        mFragmentHome = new FragmentHome();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, mFragmentHome).commit();
    }

    public void showFragmentHome() {
        mFragmentHome = new FragmentHome();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_right_to_left,
                R.anim.exit_right_to_left,
                R.anim.enter_left_to_right,
                R.anim.exit_left_to_right).replace(R.id.fl_content, mFragmentHome)
                .addToBackStack("Home").commit();
    }

    public void showFragmentListStory(String topicName){
        mFragmentListStory = new FragmentListStory();
        Bundle bundle = new Bundle();
        bundle.putString("Topic name", topicName);
        mFragmentListStory.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.enter_right_to_left,
                    R.anim.exit_right_to_left,
                    R.anim.enter_left_to_right,
                    R.anim.exit_left_to_right)
                .replace(R.id.fl_content, mFragmentListStory)
                .addToBackStack("List topic")
                .commit();
    }

    public void showFragmentContentStory(String topicName, int position){
        mFragmentContentStory = new FragmentContentStory();
        Bundle bundle = new Bundle();
        bundle.putString("Topic name", topicName);
        bundle.putInt("Position", position);
        mFragmentContentStory.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.enter_right_to_left,
                        R.anim.exit_right_to_left,
                        R.anim.enter_left_to_right,
                        R.anim.exit_left_to_right)
                .replace(R.id.fl_content, mFragmentContentStory)
                .addToBackStack("List story")
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFragmentContentStory.onActivityResult(requestCode, resultCode, data);
    }
}
