package com.teno.ailatrieuphufix;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 4/20/2017.
 */

public class GameApp extends Application {

    private String[] mBonus;
    private List<DialogData> mListDialogData;

    @Override
    public void onCreate() {
        super.onCreate();
        addBonus();
        addDialogData();
    }

    private void addBonus() {
        mBonus = new String[]{"150,000,000", "85,000,000", "60,000,000", "40,000,000", "30,000,000",
                "22,000,000", "14,000,000", "10,000,000", "6,000,000", "3,000,000",
                "2,000,000", "1,000,000", "600,000", "400,000", "200,000"};
    }

    private void addDialogData(){
        mListDialogData = new ArrayList<>();
        DialogData confirmDialog = new DialogData("Thông báo", "Bạn đã sẵn sàng chơi với chúng tôi",
                "Sẵn sàng", "Bỏ qua");
        DialogData gameOverDialog = new DialogData("Kết thúc", "Chúc bạn thành công trong cuộc số",
                "Xem BXH", "Đóng");
        DialogData stopDialog = new DialogData("Dừng lại", "Bạn muốn dừng cuộc chơi tại đây",
                "Đúng vậy", "Chơi tiếp");
        mListDialogData.add(confirmDialog);
        mListDialogData.add(gameOverDialog);
        mListDialogData.add(stopDialog);
    }

    public String[] getBonus(){
        return mBonus;
    }

    public DialogData getDialogData(String typeDialog){
        switch (typeDialog){
            case "Confirm_Play_Dialog":
                return mListDialogData.get(0);
            case "Game_Over_Dialog":
                return mListDialogData.get(1);
            case "Stop_Game_Dialog":
                return mListDialogData.get(2);
            default:
                return null;
        }
    }
}
