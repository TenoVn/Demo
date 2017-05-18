package com.teno.apptruyen.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.teno.apptruyen.app.App;
import com.teno.apptruyen.database.DatabaseManager;
import com.teno.apptruyen.item.ItemDataStory;
import com.teno.apptruyen.activities.MainActivity;
import com.teno.apptruyen.R;
import com.teno.apptruyen.adapters.ContentAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Asus on 4/24/2017.
 */

public class FragmentContentStory extends Fragment implements View.OnClickListener, ContentAdapter.IContentAdapter, ViewPager.OnPageChangeListener {

    private static final String ADD_FAVORITE_SUCCESS = "Thêm Favorite thành công";
    private static final String DELETE_FAVORITE_SUCCESS = "Xóa Favorite thành công";
    private static final String LOGIN_SUCCESS = "Đăng nhập thành công";
    private static final String NO_INTERNET = "Not connected to Internet";
    private static final String WIFI_ENABLE = "Wifi enabled";
    private static final String MOBILE_DATA_ENABLE ="Mobile data enabled" ;
    private static final String FACEBOOK_APP_PACKAGE = "com.facebook.katana";
    private static final String FACEBOOK_NOT_INSTALL = "Chưa cài facbook";

    private int TYPE_WIFI = 1;
    private int TYPE_MOBILE = 2;
    private int TYPE_NOT_CONNECTED = 0;

    private TextView mTvContent;
    private TextView mTvTopic;
    private ViewPager mVpContent;
    private FloatingActionButton mFab_main, mFab_favorite;
    private LinearLayout mLL_home, mLL_size_up, mLL_size_down, mLL_favorite, mLL_content, mLL_share;
    private Animation mShowOptions, mHideOptions, mRotateOut, mRotateIn;
    private List<ItemDataStory> mListStory;
    private int mCurrentPosition;
    private ContentAdapter mAdapter;
    private int mTextContentSize = 18;
    private DatabaseManager mDatabase;
    private static final int FAVORITE = 1;
    private static final int NOT_FAVORITE = 0;
    private int mIdStory;
    private int mFavorite;
    private ItemDataStory mItemDataStory;
    private String mTopicName;
    private String mCheckInternet;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_story, container, false);
        initFacebookLoginAndShare();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDatabase = new DatabaseManager(getContext());
        findViewByIds(view);
        initAnimation();
        Bundle bundle = getArguments();
        mTopicName = bundle.getString("Topic name");
        mCurrentPosition = bundle.getInt("Position");
        mTvTopic.setText(mTopicName);
        if(mTopicName.equals("Favorite")){
            mListStory = mDatabase.getListFavorite();
        }
        else
            mListStory = mDatabase.getListStory(((App)getContext().getApplicationContext()).getListStoryById(mTopicName));
        initViewPager();
    }

    private void initFacebookLoginAndShare() {
        FacebookSdk.sdkInitialize(getContext().getApplicationContext());
        shareDialog = new ShareDialog(this);

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");
                        Toast.makeText(getContext(), LOGIN_SUCCESS, Toast.LENGTH_SHORT).show();
                        if (ShareDialog.canShow(ShareLinkContent.class)) {
                            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                    .setContentTitle("This is a wonderful app i just found!")
                                    .setImageUrl(Uri.parse("http://hanhphucgiadinh.vn/wp-content/uploads/2011/02/truyen-cuoi-vova.jpg"))
                                    .setContentDescription(
                                            "Demo app truuyen")
                                    .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.appchacha.funnystoriesvn&hl=vi"))
                                    .build();

                            shareDialog.show(linkContent);
                        }
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getContext(), "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void initViewPager() {
        mAdapter = new ContentAdapter(this);
        mVpContent.setAdapter(mAdapter);
        mVpContent.setCurrentItem(mCurrentPosition);
        mVpContent.addOnPageChangeListener(this);
        mItemDataStory = mListStory.get(mCurrentPosition);
        mFavorite = mItemDataStory.getFavorite();
        if(mFavorite == 1){
            mFab_favorite.setImageResource(R.drawable.btn_favorite_disable);
        }
        else{
            mFab_favorite.setImageResource(R.drawable.btn_favorite);
        }
    }

    private void findViewByIds(View view) {
        mVpContent = (ViewPager)view.findViewById(R.id.vp_content);
        mTvTopic = (TextView)view.findViewById(R.id.tv_title_topic);
        mFab_main = (FloatingActionButton)view.findViewById(R.id.fab_main);
        mFab_favorite = (FloatingActionButton)view.findViewById(R.id.fab_favorite);
        mFab_main.setOnClickListener(this);
        mFab_favorite.setOnClickListener(this);
        mLL_home = (LinearLayout)view.findViewById(R.id.ll_fab_home);
        mLL_size_up = (LinearLayout)view.findViewById(R.id.ll_fab_size_up);
        mLL_size_down = (LinearLayout)view.findViewById(R.id.ll_fab_size_down);
        mLL_favorite = (LinearLayout)view.findViewById(R.id.ll_fab_favorite);
        mLL_content = (LinearLayout)view.findViewById(R.id.ll_main);
        mLL_share = (LinearLayout)view.findViewById(R.id.ll_fab_share);
        view.findViewById(R.id.btn_back).setOnClickListener(this);
        view.findViewById(R.id.fab_home).setOnClickListener(this);
        view.findViewById(R.id.fab_size_up).setOnClickListener(this);
        view.findViewById(R.id.fab_size_down).setOnClickListener(this);
        view.findViewById(R.id.fab_favorite).setOnClickListener(this);
        view.findViewById(R.id.fab_share).setOnClickListener(this);
    }

    private void initAnimation(){
        mShowOptions = AnimationUtils.loadAnimation(getContext(), R.anim.set_animation_out);
        mHideOptions = AnimationUtils.loadAnimation(getContext(), R.anim.set_animation_in);
        mRotateOut = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_out);
        mRotateIn = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_in);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back:
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.getSupportFragmentManager().popBackStack();
                break;
            case R.id.fab_main:
                if(mLL_home.getVisibility() == View.VISIBLE && mLL_size_up.getVisibility() == View.VISIBLE
                        && mLL_size_down.getVisibility() == View.VISIBLE
                        && mLL_favorite.getVisibility() == View.VISIBLE
                        && mLL_share.getVisibility() == View.VISIBLE){
                    showFloatingButton(View.GONE, mHideOptions, false, mRotateIn, 1f);
                }
                else {
                    showFloatingButton(View.VISIBLE, mShowOptions, true, mRotateOut, 0.3f);
                }
                break;
            case R.id.fab_home:
                mainActivity = (MainActivity)getActivity();
                mainActivity.showFragmentHome();
                break;
            case R.id.fab_size_up:
                mTextContentSize = mTextContentSize + 1;
                setTextSize();
                break;
            case R.id.fab_size_down:
                mTextContentSize = mTextContentSize - 1;
                setTextSize();
                break;
            case R.id.fab_share:
                mCheckInternet = getConnectivityStatusString(getContext());
                if(mCheckInternet.equals(NO_INTERNET)){
                    Toast.makeText(getContext(), NO_INTERNET, Toast.LENGTH_SHORT).show();
                    return;
                }
                checkFacebookApp();
                break;
            case R.id.fab_favorite:
                mItemDataStory = mListStory.get(mCurrentPosition);
                mFavorite = mItemDataStory.getFavorite();
                mIdStory = mItemDataStory.getIdStory();
                if(mFavorite == 1){
                    mDatabase.updateFavorite(mIdStory, NOT_FAVORITE);
                    mFab_favorite.setImageResource(R.drawable.btn_favorite);
                    if(mTopicName.equals("Favorite")){
                        mListStory = mDatabase.getListFavorite();
                    }
                    else
                        mListStory = mDatabase.getListStory(((App)getContext().getApplicationContext()).getListStoryById(mTopicName));
                    Toast.makeText(getContext(), DELETE_FAVORITE_SUCCESS, Toast.LENGTH_SHORT).show();
                }
                else{
                    mDatabase.updateFavorite(mIdStory, FAVORITE);
                    mFab_favorite.setImageResource(R.drawable.btn_favorite_disable);
                    if(mTopicName.equals("Favorite")){
                        mListStory = mDatabase.getListFavorite();
                    }
                    else
                        mListStory = mDatabase.getListStory(((App)getContext().getApplicationContext()).getListStoryById(mTopicName));
                    Toast.makeText(getContext(), ADD_FAVORITE_SUCCESS, Toast.LENGTH_SHORT).show();
                }
                mAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    private void showFloatingButton(int visible, Animation mShowOptions, boolean fillAfter, Animation mRotateOut, float alpha) {
        mLL_home.setVisibility(visible);
        mLL_size_up.setVisibility(visible);
        mLL_size_down.setVisibility(visible);
        mLL_favorite.setVisibility(visible);
        mLL_share.setVisibility(visible);
        mLL_home.startAnimation(mShowOptions);
        mLL_size_up.startAnimation(mShowOptions);
        mLL_size_down.startAnimation(mShowOptions);
        mLL_favorite.startAnimation(mShowOptions);
        mLL_share.startAnimation(mShowOptions);
        mShowOptions.setFillAfter(fillAfter);
        mFab_main.startAnimation(mRotateOut);
        mLL_content.setAlpha(alpha);
    }

    private void setTextSize() {
        View currentView = mVpContent.findViewWithTag(mCurrentPosition);
        mTvContent = (TextView) currentView.findViewById(R.id.tv_content_story);
        mTvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextContentSize);
    }

    @Override
    public int getCount() {
        return mListStory.size();
    }

    @Override
    public ItemDataStory getItem(int position) {
        return mListStory.get(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mAdapter.notifyDataSetChanged();
        mCurrentPosition = position;
        mItemDataStory = mListStory.get(mCurrentPosition);
        mFavorite = mItemDataStory.getFavorite();
        setTextSize();
        if(mFavorite == 1){
            mFab_favorite.setImageResource(R.drawable.btn_favorite_disable);
        }
        else{
            mFab_favorite.setImageResource(R.drawable.btn_favorite);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public String getConnectivityStatusString(Context context) {
        int conn = getConnectivityStatus(context);
        String status = null;
        if (conn == TYPE_WIFI) {
            status = WIFI_ENABLE;
        } else if (conn == TYPE_MOBILE) {
            status = MOBILE_DATA_ENABLE;
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = NO_INTERNET;
        }
        return status;
    }

    private void checkFacebookApp(){
        boolean installed = appInstalledOrNot(FACEBOOK_APP_PACKAGE);
        if(!installed) {
            Toast.makeText(getContext(), FACEBOOK_NOT_INSTALL, Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile", "user_friends"));
        }
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getActivity().getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed ;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
