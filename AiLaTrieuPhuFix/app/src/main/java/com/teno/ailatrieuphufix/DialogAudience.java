package com.teno.ailatrieuphufix;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Asus on 4/20/2017.
 */

public class DialogAudience extends Dialog implements View.OnClickListener {

    private int mPercentTrueCase;
    private int mPercent1;
    private int mPercent2;
    private int mPercent3;
    private int mTrueCase;
    private int mLevel;
    private List<Integer> mListWrongAnswer;

    private TextView mColA, mColB, mColC, mColD;
    private TextView mTvColA, mTvColB, mTvColC, mTvColD;

    public DialogAudience(Context context, int trueCase, int level) {
        super(context);
        mTrueCase = trueCase;
        mLevel = level;
        mListWrongAnswer = new ArrayList<>();
        setContentView(R.layout.dialog_audience);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        findViewByIds();
        createPercent();
        initColPercent();
    }

    private void createPercent(){
        Random rd = new Random();
        if(mLevel>=1&&mLevel<=5){
            mPercentTrueCase = 60 + rd.nextInt(21);
        }
        else if(mLevel>=6&&mLevel<=8){
            mPercentTrueCase = 40 + rd.nextInt(21);
        }
        else if(mLevel>=9&&mLevel<=10){
            mPercentTrueCase = 30 + rd.nextInt(11);
        }
        else {
            mPercentTrueCase = 10 + rd.nextInt(31);
        }
        int limit = 100 - mPercentTrueCase;
        mPercent1 = rd.nextInt(limit);
        int limit2 = 100 - mPercentTrueCase - mPercent1;
        mPercent2 = rd.nextInt(limit2);
        mPercent3 = 100 - mPercent1 - mPercent2 - mPercentTrueCase;
        mListWrongAnswer.add(mPercent1);
        mListWrongAnswer.add(mPercent2);
        mListWrongAnswer.add(mPercent3);
        Collections.shuffle(mListWrongAnswer);
    }

    private void findViewByIds() {
        mColA = (TextView)findViewById(R.id.tv_col_A);
        mColB = (TextView)findViewById(R.id.tv_col_B);
        mColC = (TextView)findViewById(R.id.tv_col_C);
        mColD = (TextView)findViewById(R.id.tv_col_D);

        mTvColA = (TextView)findViewById(R.id.tv_percent_A);
        mTvColB = (TextView)findViewById(R.id.tv_percent_B);
        mTvColC = (TextView)findViewById(R.id.tv_percent_C);
        mTvColD = (TextView)findViewById(R.id.tv_percent_D);

        findViewById(R.id.btn_close).setOnClickListener(this);
    }

    private void initColPercent() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 300);
//        LinearLayout.LayoutParams layoutParamsB = (LinearLayout.LayoutParams)mColB.getLayoutParams();
//        LinearLayout.LayoutParams layoutParamsC = (LinearLayout.LayoutParams)mColC.getLayoutParams();
//        LinearLayout.LayoutParams layoutParamsD = (LinearLayout.LayoutParams)mColD.getLayoutParams();
        if(mTrueCase==0){
            mColA.setPadding(0, 0, 0, paddingCol(mPercentTrueCase));
            mTvColA.setText(mPercentTrueCase + "%");
            mColB.setPadding(0, 0, 0, paddingCol(mListWrongAnswer.get(0)));
            mTvColB.setText(mListWrongAnswer.get(0) + "%");
            mColC.setPadding(0, 0, 0, paddingCol(mListWrongAnswer.get(1)));
            mTvColC.setText(mListWrongAnswer.get(1) + "%");
            mColD.setPadding(0, 0, 0, paddingCol(mListWrongAnswer.get(2)));
            mTvColD.setText(mListWrongAnswer.get(2) + "%");
        }
        else if(mTrueCase==1){
            mColB.setPadding(0, 0, 0, paddingCol(mPercentTrueCase));
            mTvColB.setText(mPercentTrueCase + "%");
            mColA.setPadding(0, 0, 0, paddingCol(mListWrongAnswer.get(0)));
            mTvColA.setText(mListWrongAnswer.get(0) + "%");
            mColC.setPadding(0, 0, 0, paddingCol(mListWrongAnswer.get(1)));
            mTvColC.setText(mListWrongAnswer.get(1) + "%");
            mColD.setPadding(0, 0, 0, paddingCol(mListWrongAnswer.get(2)));
            mTvColD.setText(mListWrongAnswer.get(2) + "%");
        }
        else if(mTrueCase==2){
            mColC.setPadding(0, 0, 0, paddingCol(mPercentTrueCase));
            mTvColC.setText(mPercentTrueCase + "%");
            mColB.setPadding(0, 0, 0, paddingCol(mListWrongAnswer.get(0)));
            mTvColB.setText(mListWrongAnswer.get(0) + "%");
            mColA.setPadding(0, 0, 0, paddingCol(mListWrongAnswer.get(1)));
            mTvColA.setText(mListWrongAnswer.get(1) + "%");
            mColD.setPadding(0, 0, 0, paddingCol(mListWrongAnswer.get(2)));
            mTvColD.setText(mListWrongAnswer.get(2) + "%");
        }
        else if(mTrueCase==3){
            mColD.setPadding(0, 0, 0, paddingCol(mPercentTrueCase));
            mTvColD.setText(mPercentTrueCase + "%");
            mColB.setPadding(0, 0, 0, paddingCol(mListWrongAnswer.get(0)));
            mTvColB.setText(mListWrongAnswer.get(0) + "%");
            mColC.setPadding(0, 0, 0, paddingCol(mListWrongAnswer.get(1)));
            mTvColC.setText(mListWrongAnswer.get(1) + "%");
            mColA.setPadding(0, 0, 0, paddingCol(mListWrongAnswer.get(2)));
            mTvColA.setText(mListWrongAnswer.get(2) + "%");
        }
    }

    private int paddingCol(int percent) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        int padding = (int) (percent * scale + 0.5f);
        return padding;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_close:
                dismiss();
                break;
            default:
                break;
        }
    }
}
