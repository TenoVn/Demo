package com.teno.ailatrieuphufix;

import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Asus on 4/20/2017.
 */

public class FragmentPlay extends Fragment implements View.OnClickListener{

    private static final int ANSWER_A_ID = 0;
    private static final int ANSWER_B_ID = 1;
    private static final int ANSWER_C_ID = 2;
    private static final int ANSWER_D_ID = 3;
    private static final int HELP_STOP_ID = 4;
    private static final int HELP_CHANGE_QUESTION_ID = 5;
    private static final int HELP_5050_ID = 6;
    private static final int HELP_AUDIENCE_ID = 7;
    private static final int HELP_CALL_ID = 8;
    private static final String AUDIO_RULES = "Luật chơi";
    private static final String AUDIO_READY = "Sẵn sàng";

    private TextView mTvLevel;
    private TextView mTvQuestion;
    private TextView mTvTime;
    private LinearLayout mRowAnswer;
    private LinearLayout mColHelp;
    private DialogBonus mDialogBonus;
    private DialogConfirm mDialogConfirm;
    private DatabaseManager mDatabase;
    private List<Question> mQuestions;
    private List<TextView> mListTvAnswer;
    private List<Button> mListHelp;
    private int mTrueCase;
    private int mLevel;
    private int index;
    private int mHelp;
    private boolean isUseChangeQuestion, isUse5050, isUseAudience, isUseCall;
    private boolean isRunning;
    private MyAsyncTask mAsyncTask;
    private int mChoose;
    private MediaPlayer mMediaPlayer;
    private MediaPlayer mMediaPlayer2;
    private MediaPlayer mMediaPlayer3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewByIds(view);
        init();
        showBonusDialog();
        mDialogBonus.createSound();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mDialogBonus.isDismiss()){
                    handler.removeCallbacks(this);
                }
                else {
                    mDialogBonus.dismiss();
                    handler.removeCallbacks(this);
                    showConfirmDialog();
                }
            }
        },9000);
        mDialogBonus.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                AudioManager audioManager = new AudioManager(mediaPlayer);
                audioManager.playSound(getContext(), audioManager.getQuestion(index));
                MyAsyncTask mAsyncTask = new MyAsyncTask();
                mAsyncTask.execute(Integer.parseInt(mTvTime.getText().toString()));
            }
        });
    }

    private void init() {
        index = 0;
        isRunning = true;
        isUseChangeQuestion = false;
        isUse5050 = false;
        isUseAudience = false;
        isUseCall = false;
        mListTvAnswer = new ArrayList<>();
        mListHelp = new ArrayList<>();

        initQuestion();
        initDefaultAnswer();
        initHelp();
    }

    private void findViewByIds(View view) {
        mTvLevel = (TextView)view.findViewById(R.id.tv_level_question);
        mTvQuestion = (TextView)view.findViewById(R.id.tv_question);
        mTvTime = (TextView)view.findViewById(R.id.tv_time);
        mRowAnswer = (LinearLayout)view.findViewById(R.id.ll_row_answer);
        mColHelp = (LinearLayout)view.findViewById(R.id.ll_col_help);
    }

    private void initQuestion() {
        mDatabase = new DatabaseManager(getContext());
        mQuestions = mDatabase.query15Question();
    }

    private void initDefaultAnswer(){
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer2 = new MediaPlayer();
        mMediaPlayer3 = new MediaPlayer();
        Question question = mQuestions.get(index);
        mTrueCase = question.getTrueCase();
        mLevel = question.getLevel();
        mTvQuestion.setText(question.getAks());
        mTvLevel.setText("Câu "+mLevel);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for(int i = 0; i<4; i++){
            TextView tv = (TextView) inflater.inflate(R.layout.item_answer, mRowAnswer, false);
            if(i == 0){
                tv.setText(question.getRa());
            }
            else if(i == 1){
                tv.setText(question.getRb());
            }
            else if(i == 2){
                tv.setText(question.getRc());
            }
            else
                tv.setText(question.getRd());
            tv.setId(i);
            tv.setOnClickListener(this);
            mRowAnswer.addView(tv);
            mListTvAnswer.add(tv);
        }
    }

    private void nextQuestion(){
        mMediaPlayer.release();
        mMediaPlayer2.release();
        mMediaPlayer3.release();
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer2 = new MediaPlayer();
        mMediaPlayer3 = new MediaPlayer();
        mTvTime.setText("30");
        isRunning = false;
        isRunning = true;
        enableAll();
        index++;
        Question question = mQuestions.get(index);
        createQuestion(question);
    }

    private void createQuestion(Question question) {
        String[] listAnswer = {question.getRa(), question.getRb(), question.getRc(), question.getRd()};
        mTrueCase = question.getTrueCase();
        mLevel = question.getLevel();
        mTvQuestion.setText(question.getAks());
        mTvLevel.setText("Câu "+mLevel);
        for(int i = 0; i<4; i++){
            TextView tv = mListTvAnswer.get(i);
            tv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.animation_question_click));
            tv.setText(listAnswer[i]);
        }
    }

    private void initHelp(){
        Drawable help_stop = ContextCompat.getDrawable(getContext(), R.drawable.help_stop_selector);
        Drawable help_change_question = ContextCompat.getDrawable(getContext(), R.drawable.help_change_question_selector);
        Drawable help_50_50 = ContextCompat.getDrawable(getContext(), R.drawable.help_5050_selector);
        Drawable help_audience = ContextCompat.getDrawable(getContext(), R.drawable.help_audience_selector);
        Drawable help_call = ContextCompat.getDrawable(getContext(), R.drawable.help_call_selector);
        Drawable[] drawableRes = {help_stop, help_change_question, help_50_50, help_audience, help_call};

        LayoutInflater inflater = LayoutInflater.from(getContext());
        for(int i = 0; i<5; i++){
            Button btn = (Button)inflater.inflate(R.layout.item_help, mColHelp, false);
            btn.setId(i+4);
            btn.setBackground(drawableRes[i]);
            btn.setOnClickListener(this);
            mColHelp.addView(btn);
            mListHelp.add(btn);
        }
    }

    private void showBonusDialog(){
        mDialogBonus = new DialogBonus(getContext());
        mDialogBonus.show();
    }

    private void showConfirmDialog(){
        createDialog("Confirm_Play_Dialog");
    }

    private void showGameOverDialog(){
        createDialog("Game_Over_Dialog");
    }

    private void showStopDialog(){
        createDialog("Stop_Game_Dialog");
    }

    private void createDialog(String stop_game_dialog) {
        DialogData dialogData = ((GameApp) getContext().getApplicationContext())
                .getDialogData(stop_game_dialog);
        mDialogConfirm = new DialogConfirm(getContext(), dialogData);
        mDialogConfirm.show();
    }

    private void disableAll(){
        disableAnswer();
        disableHelp();
    }

    private void enableAll(){
        enableAnswer();
        enableHelp();
    }

    private void disableAnswer(){
        for(TextView tv: mListTvAnswer){
            tv.setEnabled(false);
        }
    }

    private void enableAnswer(){
        for(TextView tv: mListTvAnswer){
            tv.setEnabled(true);
        }
    }

    private void disableHelp(){
        for(Button iv: mListHelp){
            iv.setEnabled(false);
        }
    }

    private void enableAllHelp(){
        for(Button iv: mListHelp){
            iv.setEnabled(true);
        }
    }

    private void enableHelp(){
        enableAllHelp();
        if(isUseChangeQuestion){
            mListHelp.get(1).setEnabled(false);
        }
        if(isUse5050){
            mListHelp.get(2).setEnabled(false);
        }
        if(isUseAudience){
            mListHelp.get(3).setEnabled(false);
        }
        if(isUseCall){
            mListHelp.get(4).setEnabled(false);
        }
    }

    private void onClickAnswerEvent(int id){
        TextView tv = mListTvAnswer.get(id);
        AnimationDrawable animationDrawable = (AnimationDrawable)tv.getBackground();
        animationDrawable.start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case ANSWER_A_ID:
                isRunning = false;
                onClickAnswerEvent(ANSWER_A_ID);
                disableAll();
                checkAnswer(ANSWER_A_ID);
                break;
            case ANSWER_B_ID:
                isRunning = false;
                onClickAnswerEvent(ANSWER_B_ID);
                disableAll();
                checkAnswer(ANSWER_B_ID);
                break;
            case ANSWER_C_ID:
                isRunning = false;
                onClickAnswerEvent(ANSWER_C_ID);
                disableAll();
                checkAnswer(ANSWER_C_ID);
                break;
            case ANSWER_D_ID:
                isRunning = false;
                onClickAnswerEvent(ANSWER_D_ID);
                disableAll();
                checkAnswer(ANSWER_D_ID);
                break;
            case HELP_STOP_ID:
                showStopDialog();
                break;
            case HELP_CHANGE_QUESTION_ID:
                changeQuestion();
                break;
            case HELP_5050_ID:
                help5050();
                mHelp = HELP_5050_ID;
                break;
            case HELP_AUDIENCE_ID:
                audience();
                break;
            case HELP_CALL_ID:
                proHelp();
                break;
        }
    }

    private void isUseHelp(int help){
        switch (help){
            case HELP_CHANGE_QUESTION_ID:
                isUseChangeQuestion = true;
                break;
            case HELP_5050_ID:
                isUse5050 = true;
                break;
            case HELP_AUDIENCE_ID:
                isUseAudience = true;
                break;
            case HELP_CALL_ID:
                isUseCall = true;
                break;
        }
    }

    private void changeQuestion(){
        mListHelp.get(1).setEnabled(false);
        mListHelp.get(1).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.help_change_question_x));
        Question newQuestion = mDatabase.getNewQuestion(mLevel);
        createQuestion(newQuestion);
    }

    private void help5050() {
        mListHelp.get(2).setEnabled(false);
        mListHelp.get(2).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.help_5050_x));
        List<TextView> wrongAnswer = new ArrayList<>();
        for (TextView tv : mListTvAnswer) {
            if (tv != mListTvAnswer.get(mTrueCase)) {
                wrongAnswer.add(tv);
            }
        }
        Random rd = new Random();
        int random1 = rd.nextInt(3);
        TextView tv1 = wrongAnswer.get(random1);
        wrongAnswer.remove(random1);
        int random2 = rd.nextInt(2);
        TextView tv2 = wrongAnswer.get(random2);
        tv1.setText("");
        tv2.setText("");
        tv1.setEnabled(false);
        tv2.setEnabled(false);
    }

    private void audience(){
        mListHelp.get(3).setEnabled(false);
        mListHelp.get(3).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.help_audience_x));
        DialogAudience dialogAudience = new DialogAudience(getContext(), mTrueCase, mLevel);
        dialogAudience.show();
        isRunning = false;
        dialogAudience.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                isRunning = true;
            }
        });
    }

    private void proHelp(){
        mListHelp.get(4).setEnabled(false);
        mListHelp.get(4).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.help_call_x));
        DialogProHelp dialogProHelp = new DialogProHelp(getContext(), mTrueCase);
        dialogProHelp.show();
        isRunning = false;
        dialogProHelp.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                isRunning = true;
            }
        });
    }

    private void checkAnswer(int choose) {
        mMediaPlayer = new MediaPlayer();
        final AudioManager audioManager = new AudioManager(mMediaPlayer);
        audioManager.playSound(getContext(), audioManager.getAudioChoose(choose));
        mChoose = choose;
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mMediaPlayer2 = new MediaPlayer();
                AudioManager audioManager2 = new AudioManager(mMediaPlayer2);
                TextView tvChoose = mListTvAnswer.get(mChoose);
                if(mTrueCase == mChoose){
                    tvChoose.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.animation_question_true));
                    final AnimationDrawable animationDrawable = (AnimationDrawable)tvChoose.getBackground();
                    animationDrawable.start();
                    audioManager2.playSound(getContext(), audioManager2.getAudioTrue(mChoose));
                    mMediaPlayer2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            animationDrawable.stop();
                            mDialogBonus.updateDialog(14-mLevel);
                            mDialogBonus.getWindow().setLayout(900, WindowManager.LayoutParams.MATCH_PARENT);
                            mDialogBonus.getWindow().getAttributes().windowAnimations = R.style.DialogBonusAnimation;
                            mDialogBonus.show();
                            isUseHelp(mHelp);
                            nextQuestion();
                        }
                    });
                }
                else {
                    TextView tvTrue = mListTvAnswer.get(mTrueCase);
                    tvChoose.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.animation_question_wrong));
                    tvTrue.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.animation_question_true));
                    mMediaPlayer3 = new MediaPlayer();
                    AudioManager audioManager3 = new AudioManager(mMediaPlayer3);
                    final AnimationDrawable animationDrawable = (AnimationDrawable)tvChoose.getBackground();
                    animationDrawable.start();
                    final AnimationDrawable animationDrawable2 = (AnimationDrawable)tvTrue.getBackground();
                    animationDrawable2.start();
                    audioManager3.playSound(getContext(), audioManager2.getAudioLose(mTrueCase));
                    mMediaPlayer3.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            showGameOverDialog();
                        }
                    });
                }
            }
        });
    }

    private class MyAsyncTask extends AsyncTask<Integer, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Integer... params) {
            int time = params[0];
            while (time >= 0) {
                try {
                    if (isRunning) {
                        Thread.sleep(1000);
                        publishProgress(time);
                        time--;
                    } else {
                        return true;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i("TAG", values[0] + "");
            mTvTime.setText(values[0] + "");
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            if (!bool) {
                showGameOverDialog();
            }
            else {
                new MyAsyncTask().execute(Integer.parseInt(mTvTime.getText().toString()));
            }
        }
    }

    @Override
    public void onDestroyView() {
        isRunning = false;
        mTvTime.setText("30");
        super.onDestroyView();
    }
}
