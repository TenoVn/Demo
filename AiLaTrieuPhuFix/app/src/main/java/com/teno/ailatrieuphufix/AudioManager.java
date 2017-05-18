package com.teno.ailatrieuphufix;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;
import java.util.Random;

/**
 * Created by Asus on 4/20/2017.
 */

public class AudioManager {

    private static final int ANSWER_A = 0;
    private static final int ANSWER_B = 1;
    private static final int ANSWER_C = 2;
    private static final int ANSWER_D = 3;

    private static final String[] AUDIO_READY = {"ready", "ready_b", "ready_c"};
    private static final String[] AUDIO_RULES = {"luatchoi_b","luatchoi_c"};

    private static final String[] AUDIO_ANS_A = {"ans_a", "ans_a2"};
    private static final String[] AUDIO_ANS_B = {"ans_b", "ans_b2"};
    private static final String[] AUDIO_ANS_C = {"ans_c", "ans_c2"};
    private static final String[] AUDIO_ANS_D = {"ans_d", "ans_d2"};

    private static final String[] AUDIO_TRUE_A = {"true_a", "true_a2", "true_a3"};
    private static final String[] AUDIO_TRUE_B = {"true_b", "true_b2", "true_b3"};
    private static final String[] AUDIO_TRUE_C = {"true_c", "true_c2", "true_c3"};
    private static final String[] AUDIO_TRUE_D = {"true_d2", "true_d3"};

    private static final String[] AUDIO_LOSE_A = {"lose_a", "lose_a2"};
    private static final String[] AUDIO_LOSE_B = {"lose_b", "lose_b2"};
    private static final String[] AUDIO_LOSE_C = {"lose_c", "lose_c2"};
    private static final String[] AUDIO_LOSE_D = {"lose_d", "lose_d2"};

    private static final String[] QUESTIONS = {"ques1", "ques2", "ques3", "ques4", "ques5", "ques6",
            "ques7", "ques8", "ques9", "ques10", "ques11", "ques12", "ques13", "ques14", "ques15"};

    private MediaPlayer mMediaPlayer;

    public AudioManager(MediaPlayer mediaPlayer) {
        mMediaPlayer = mediaPlayer;
    }

    public void playSound(Context context, String audioName){
        try {
            AssetFileDescriptor afd = context.getAssets().openFd("sounds/"+ audioName + ".mp3");
            mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopSound(){
        if(mMediaPlayer != null){
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public String getAudio(String typeAudio){
        Random rd = new Random();
        switch (typeAudio){
            case "Luật chơi":
                int audio = rd.nextInt(AUDIO_RULES.length);
                return AUDIO_RULES[audio];
            case "Sẵn sàng":
                audio = rd.nextInt(AUDIO_READY.length);
                return AUDIO_READY[audio];
            default:
                return null;
        }
    }

    public String getAudioChoose(int answerChoose){
        Random rd = new Random();
        switch (answerChoose){
            case ANSWER_A:
                int audio = rd.nextInt(AUDIO_ANS_A.length);
                return AUDIO_ANS_A[audio];
            case ANSWER_B:
                audio = rd.nextInt(AUDIO_ANS_B.length);
                return AUDIO_ANS_B[audio];
            case ANSWER_C:
                audio = rd.nextInt(AUDIO_ANS_C.length);
                return AUDIO_ANS_C[audio];
            default:
                audio = rd.nextInt(AUDIO_ANS_D.length);
                return AUDIO_ANS_D[audio];
        }
    }

    public String getAudioLose(int answerTrue){
        Random rd = new Random();
        switch (answerTrue){
            case ANSWER_A:
                int audio = rd.nextInt(AUDIO_LOSE_A.length);
                return AUDIO_LOSE_A[audio];
            case ANSWER_B:
                audio = rd.nextInt(AUDIO_LOSE_B.length);
                return AUDIO_LOSE_B[audio];
            case ANSWER_C:
                audio = rd.nextInt(AUDIO_LOSE_C.length);
                return AUDIO_LOSE_C[audio];
            default:
                audio = rd.nextInt(AUDIO_LOSE_D.length);
                return AUDIO_LOSE_D[audio];
        }
    }

    public String getAudioTrue(int answerTrue){
        Random rd = new Random();
        switch (answerTrue){
            case ANSWER_A:
                int audio = rd.nextInt(AUDIO_TRUE_A.length);
                return AUDIO_TRUE_A[audio];
            case ANSWER_B:
                audio = rd.nextInt(AUDIO_TRUE_B.length);
                return AUDIO_TRUE_B[audio];
            case ANSWER_C:
                audio = rd.nextInt(AUDIO_TRUE_C.length);
                return AUDIO_TRUE_C[audio];
            default:
                audio = rd.nextInt(AUDIO_TRUE_D.length);
                return AUDIO_TRUE_D[audio];
        }
    }

    public String getQuestion(int i){
        return QUESTIONS[i];
    }
}
