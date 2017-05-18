package com.teno.ailatrieuphufix;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Asus on 5/18/2017.
 */

public class DialogProHelp extends Dialog implements View.OnClickListener {

    private LinearLayout mLlChoosePro;
    private LinearLayout mLlProAnswer;
    private ImageView mIvProChoose;
    private TextView mTvProChoose, mTvNamePro;
    private int mTrueCase;

    public DialogProHelp(Context context, int trueAnswer) {
        super(context);
        setContentView(R.layout.dialog_pro_help);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        mTrueCase = trueAnswer;
        mLlChoosePro = (LinearLayout)findViewById(R.id.ll_choose_pro);
        mLlProAnswer = (LinearLayout)findViewById(R.id.ll_pro_answer);
        mIvProChoose = (ImageView)findViewById(R.id.iv_pro_choose);
        mTvProChoose = (TextView)findViewById(R.id.tv_pro_choose);
        mTvNamePro = (TextView)findViewById(R.id.tv_name_pro);

        findViewById(R.id.anhxtanh_help).setOnClickListener(this);
        findViewById(R.id.billgate_help).setOnClickListener(this);
        findViewById(R.id.khongminh_help).setOnClickListener(this);
        findViewById(R.id.ngobaochau_help).setOnClickListener(this);
        findViewById(R.id.btn_close).setOnClickListener(this);
    }

    private String getTrueCase(int trueCase){
        switch (trueCase){
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
            default:
                return "D";
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.anhxtanh_help:
                choosePro(R.drawable.h1_anhxtanh, "Anhxtanh");
                break;
            case R.id.billgate_help:
                choosePro(R.drawable.h2_billgate, "BillGate");
                break;
            case R.id.khongminh_help:
                choosePro(R.drawable.h3_khongminh, "Khổng Minh");
                break;
            case R.id.ngobaochau_help:
                choosePro(R.drawable.h4_ngobaochau, "Ngô Báo châu");
                break;
            case R.id.btn_close:
                dismiss();
                break;
            default:
                break;
        }
    }

    private void choosePro(int proImage, String proName) {
        mLlChoosePro.setVisibility(View.GONE);
        mLlProAnswer.setVisibility(View.VISIBLE);
        mIvProChoose.setImageResource(proImage);
        mTvNamePro.setText(proName);
        mTvProChoose.setText("Tôi nghĩ đáp án này là " + getTrueCase(mTrueCase));
    }
}
