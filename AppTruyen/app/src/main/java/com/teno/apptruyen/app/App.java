package com.teno.apptruyen.app;

import android.app.Application;

import com.teno.apptruyen.item.ItemDataStory;
import com.teno.apptruyen.database.DatabaseManager;

import java.util.List;

/**
 * Created by Asus on 4/25/2017.
 */

public class App extends Application {

    private static final int VOVA =  1;
    private static final int DAN_GIAN =  2;
    private static final int GIA_DINH =  3;
    private static final int TINH_YEU =  4;
    private static final int TIEU_LAM =  5;
    private static final int CONG_SO =  6;
    private static final int HOC_SINH =  7;
    private static final int TRUYEN_NGAN =  8;
    private static final int CUOI_18 =  9;
    private static final int Y_HOC =  10;
    private static final int TAM_QUOC =  11;
    private static final int THE_GIOI =  12;
    private static final int TAY_DU_KY_CHE =  13;
    private static final int TRUYEN_KHAC =  14;

    private List<ItemDataStory> mListStoryVova;
    private List<ItemDataStory> mListStoryDangian;
    private List<ItemDataStory> mListStoryGiadinh;
    private List<ItemDataStory> mListStoryTinhyeu;
    private List<ItemDataStory> mListStoryTieulam;
    private List<ItemDataStory> mListStoryCongso;
    private List<ItemDataStory> mListStoryHocsinh;
    private List<ItemDataStory> mListStoryTruyenngan;
    private List<ItemDataStory> mListStoryCuoi18;
    private List<ItemDataStory> mListStoryYhoc;
    private List<ItemDataStory> mListStoryTamquoc;
    private List<ItemDataStory> mListStoryThegioi;
    private List<ItemDataStory> mListStoryTaydukiche;
    private List<ItemDataStory> mListStoryTruyenkhac;
    private DatabaseManager mDatabaseManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mDatabaseManager = new DatabaseManager(this);
        initListStory();
    }

    private void initListStory() {
        mListStoryVova = mDatabaseManager.getListStory(VOVA);
        mListStoryDangian = mDatabaseManager.getListStory(DAN_GIAN);
        mListStoryGiadinh = mDatabaseManager.getListStory(GIA_DINH);
        mListStoryTinhyeu = mDatabaseManager.getListStory(TINH_YEU);
        mListStoryTieulam = mDatabaseManager.getListStory(TIEU_LAM);
        mListStoryCongso = mDatabaseManager.getListStory(CONG_SO);
        mListStoryHocsinh = mDatabaseManager.getListStory(HOC_SINH);
        mListStoryTruyenngan = mDatabaseManager.getListStory(TRUYEN_NGAN);
        mListStoryCuoi18 = mDatabaseManager.getListStory(CUOI_18);
        mListStoryYhoc = mDatabaseManager.getListStory(Y_HOC);
        mListStoryTamquoc = mDatabaseManager.getListStory(TAM_QUOC);
        mListStoryThegioi = mDatabaseManager.getListStory(THE_GIOI);
        mListStoryTaydukiche = mDatabaseManager.getListStory(TAY_DU_KY_CHE);
        mListStoryTruyenkhac = mDatabaseManager.getListStory(TRUYEN_KHAC);
    }

    public int getListStoryById(String type){
        switch (type){
            case "Vova":
                return VOVA;
            case "Dân gian":
                return DAN_GIAN;
            case "Gia đình":
                return GIA_DINH;
            case "Tình yêu":
                return TINH_YEU;
            case "Tiếu lâm":
                return TIEU_LAM;
            case "Công sở":
                return CONG_SO;
            case "Học sinh":
                return HOC_SINH;
            case "Truyện ngắn":
                return TRUYEN_NGAN;
            case "Cười 18":
                return CUOI_18;
            case "Y học":
                return Y_HOC;
            case "Tam quốc":
                return TAM_QUOC;
            case "Thế giới":
                return THE_GIOI;
            case "Tây du ký chế":
                return TAY_DU_KY_CHE;
            default:
                return TRUYEN_KHAC;
        }
    }

    public List<ItemDataStory> getListStory(String type){
        switch (getListStoryById(type)){
            case VOVA:
                return mListStoryVova;
            case DAN_GIAN:
                return mListStoryDangian;
            case GIA_DINH:
                return mListStoryGiadinh;
            case TINH_YEU:
                return mListStoryTinhyeu;
            case TIEU_LAM:
                return mListStoryTieulam;
            case CONG_SO:
                return mListStoryCongso;
            case HOC_SINH:
                return mListStoryHocsinh;
            case TRUYEN_NGAN:
                return mListStoryTruyenngan;
            case CUOI_18:
                return mListStoryCuoi18;
            case Y_HOC:
                return mListStoryYhoc;
            case TAM_QUOC:
                return mListStoryTamquoc;
            case THE_GIOI:
                return mListStoryThegioi;
            case TAY_DU_KY_CHE:
                return mListStoryTaydukiche;
            case TRUYEN_KHAC:
                return mListStoryTruyenkhac;
            default:
                return null;
        }
    }
}
