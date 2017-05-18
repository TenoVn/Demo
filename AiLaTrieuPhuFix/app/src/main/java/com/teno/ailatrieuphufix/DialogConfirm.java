package com.teno.ailatrieuphufix;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Asus on 4/20/2017.
 */

public class DialogConfirm extends Dialog implements View.OnClickListener {

    private TextView mTvTitle, mTvMessage;
    private Button mButtonPositive, mButtonNegative;
    private DialogData mData;
    private Context mContext;

    public DialogConfirm(Context context, DialogData data) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_confirm);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mData = data;
        mContext = context;

        findViewByIds();
        setViews();
    }

    private void setViews() {
        mTvMessage.setText(mData.getMessage());
        mTvTitle.setText(mData.getTitle());
        mButtonNegative.setText(mData.getNegativeText());
        mButtonPositive.setText(mData.getPositiveText());
    }

    private void findViewByIds() {
        mTvMessage = (TextView)findViewById(R.id.tv_dialog_message);
        mTvTitle = (TextView)findViewById(R.id.tv_dialog_title);
        mButtonNegative = (Button)findViewById(R.id.btn_dialog_left);
        mButtonNegative.setOnClickListener(this);
        mButtonPositive = (Button)findViewById(R.id.btn_dialog_right);
        mButtonPositive.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String positiveMessage = mButtonPositive.getText().toString();
        String negativeMessage = mButtonNegative.getText().toString();
        switch (v.getId()){
            case R.id.btn_dialog_right:
                if(positiveMessage.equals("Đúng vậy")){
                    returnToMenu();
                }
                else if(positiveMessage.equals("Sẵn sàng")){
                    dismiss();
                }
                else if(positiveMessage.equals("Có")){

                }
                else if(positiveMessage.equals("Đóng")){
                    returnToMenu();
                }
                break;
            case R.id.btn_dialog_left:
                if(negativeMessage.equals("Bỏ qua")){
                    returnToMenu();
                }
                else if(negativeMessage.equals("Chơi tiếp")){
                    dismiss();
                }
                else if(negativeMessage.equals("Đóng")){
                    returnToMenu();
                }
                break;
            default:
                break;
        }
    }

    private void returnToMenu() {
        dismiss();
        MainActivity activity = (MainActivity)mContext;
        activity.showFragmentMenu();
    }
}
