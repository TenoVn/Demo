package com.teno.ailatrieuphufix;

/**
 * Created by Asus on 4/20/2017.
 */

public class DialogData {
    private String mTitle;
    private String mMessage;
    private String mPositiveText;
    private String mNegativeText;

    public DialogData(String title, String message, String positiveText, String negativeText) {
        mTitle = title;
        mMessage = message;
        mPositiveText = positiveText;
        mNegativeText = negativeText;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getPositiveText() {
        return mPositiveText;
    }

    public String getNegativeText() {
        return mNegativeText;
    }
}
