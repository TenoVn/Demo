package com.teno.apptruyen.item;

/**
 * Created by Asus on 4/24/2017.
 */

public class ItemDataStory {
    private int mIdStory;
    private String mNameStory;
    private String mContentStory;
    private String mCategory;
    private int mFavorite;

    public ItemDataStory(int idStory, String nameStory, String contentStory, String category, int favorite) {
        mIdStory = idStory;
        mNameStory = nameStory;
        mContentStory = contentStory;
        mCategory = category;
        mFavorite = favorite;
    }

    public String getNameStory() {
        return mNameStory;
    }

    public String getContentStory() {
        return mContentStory;
    }

    public int getIdStory() {
        return mIdStory;
    }

    public int getFavorite() {
        return mFavorite;
    }
}
