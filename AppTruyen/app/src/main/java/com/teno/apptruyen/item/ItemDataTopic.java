package com.teno.apptruyen.item;

/**
 * Created by Asus on 4/24/2017.
 */

public class ItemDataTopic {
    private int mIdImageTopic;
    private String mNameTopic;

    public ItemDataTopic(int idImageTopic, String nameTopic) {

        mIdImageTopic = idImageTopic;
        mNameTopic = nameTopic;
    }

    public int getIdImageTopic() {
        return mIdImageTopic;
    }

    public String getNameTopic() {
        return mNameTopic;
    }
}
