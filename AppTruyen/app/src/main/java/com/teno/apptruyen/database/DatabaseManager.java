package com.teno.apptruyen.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.teno.apptruyen.item.ItemDataStory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 4/24/2017.
 */

public class DatabaseManager{

    private static final  String TAG = DatabaseManager.class.getSimpleName();

    private static final String PATH_DATABASE = Environment.getDataDirectory()
                + File.separator + "data"
                + File.separator + "com.teno.apptruyen"
                + File.separator + "database";
    private static final String DATABASE_NAME = "truyen";

    private SQLiteDatabase mDatabase;
    private Context mContext;

    public DatabaseManager(Context context) {
        mContext = context;
        copyDatabase();
    }

    private void copyDatabase() {
        new File(PATH_DATABASE).mkdir();
        String pathFile = PATH_DATABASE + File.separator + DATABASE_NAME;
        File fileDB = new File(pathFile);
        if (fileDB.exists()) {
            return;
        }
        try {
            InputStream in = mContext.getAssets().open(DATABASE_NAME);
            fileDB.createNewFile();
            FileOutputStream out = new FileOutputStream(fileDB);

            byte b[] = new byte[1024];
            int le = in.read(b);
            while (le >= 0) {
                out.write(b, 0, le);
                le = in.read(b);
            }
            out.close();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openDatabase() {
        if (mDatabase == null || !mDatabase.isOpen()) {
            mDatabase = SQLiteDatabase.openDatabase(PATH_DATABASE +
                    File.separator + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        }
    }

    private void closeDatabase() {
        if (mDatabase != null && mDatabase.isOpen()) {
            mDatabase.close();
        }
    }

    public List<ItemDataStory> getListStory(int type){
        List<ItemDataStory> listStory = new ArrayList<>();
        String sql = "SELECT * FROM TruyenCuoi WHERE typeId = "+type;
        openDatabase();
        Cursor cursor = mDatabase.rawQuery(sql, null);
        if(cursor!=null){
            cursor.moveToFirst();
            int indexID = cursor.getColumnIndex("id");
            int indexName = cursor.getColumnIndex("name");
            int indexContent = cursor.getColumnIndex("content");
            int indexCategory = cursor.getColumnIndex("category");
            int indexFavorite = cursor.getColumnIndex("favorite");
            while (!cursor.isAfterLast()){

                int id = cursor.getInt(indexID);
                String name = cursor.getString(indexName);
                String content = cursor.getString(indexContent);
                String category = cursor.getString(indexCategory);
                int favorite = cursor.getInt(indexFavorite);
                ItemDataStory itemDataStory = new ItemDataStory(id, name, content, category, favorite);
                listStory.add(itemDataStory);
                cursor.moveToNext();
            }
        }
        closeDatabase();
        return listStory;
    }

    public void deleteDatabase(){
        mContext.deleteDatabase(PATH_DATABASE + File.separator + DATABASE_NAME);
    }

    public void updateFavorite(int id, int like){
        openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("favorite", like);
        int ret = mDatabase.update("TruyenCuoi", contentValues, "id = ?", new String[]{String.valueOf(id)});
        if(ret == 0){
            Log.d(TAG, "UPDATE THAT BAI");
        }
        else
            Log.d(TAG, "UPDATE THANH CONG");
        closeDatabase();
    }

    public List<ItemDataStory> getListFavorite() {
        List<ItemDataStory> listFavorite = new ArrayList<>();
        String sql = "SELECT * FROM TruyenCuoi WHERE favorite = " + 1;
        openDatabase();
        Cursor cursor = mDatabase.rawQuery(sql, null);
        if(cursor!=null){
            cursor.moveToFirst();
            int indexID = cursor.getColumnIndex("id");
            int indexName = cursor.getColumnIndex("name");
            int indexContent = cursor.getColumnIndex("content");
            int indexCategory = cursor.getColumnIndex("category");
            int indexFavorite = cursor.getColumnIndex("favorite");
            while (!cursor.isAfterLast()){

                int id = cursor.getInt(indexID);
                String name = cursor.getString(indexName);
                String content = cursor.getString(indexContent);
                String category = cursor.getString(indexCategory);
                int favorite = cursor.getInt(indexFavorite);
                ItemDataStory itemDataStory = new ItemDataStory(id, name, content, category, favorite);
                listFavorite.add(itemDataStory);
                cursor.moveToNext();
            }
        }
        closeDatabase();
        return listFavorite;
    }
}
