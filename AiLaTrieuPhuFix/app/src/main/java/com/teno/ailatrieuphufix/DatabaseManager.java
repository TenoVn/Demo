package com.teno.ailatrieuphufix;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Asus on 4/19/2017.
 */

public class DatabaseManager {
    private static final String PATH_DATABASE = Environment.getDataDirectory()
                    + File.separator + "data"
                    + File.separator + "com.teno.ailatrieuphufix"
                    + File.separator + "database";
    private static final String DATABASE_NAME = "trieuphu_games";

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

    public Question getNewQuestion(int currentLevel){
        Question question = new Question();
        String sql = "SELECT * FROM question WHERE level = "+currentLevel+" ORDER BY Random() LIMIT 1";
        openDatabase();
        Cursor cursor = mDatabase.rawQuery(sql, null);
        if(cursor!=null){
            cursor.moveToFirst();
            int indexID = cursor.getColumnIndex("id");
            int indexAsk = cursor.getColumnIndex("ask");
            int indexRa = cursor.getColumnIndex("ra");
            int indexRb = cursor.getColumnIndex("rb");
            int indexRc = cursor.getColumnIndex("rc");
            int indexRd = cursor.getColumnIndex("rd");
            int indexLevel = cursor.getColumnIndex("level");

            int id = cursor.getInt(indexID);
            String aks = cursor.getString(indexAsk);
            String rA = cursor.getString(indexRa);
            String rB = cursor.getString(indexRb);
            String rC = cursor.getString(indexRc);
            String rD = cursor.getString(indexRd);
            int level = cursor.getInt(indexLevel);
            List<String> ans = new ArrayList<>();
            ans.add(rA);
            ans.add(rB);
            ans.add(rC);
            ans.add(rD);
            Collections.shuffle(ans);
            int trueCase = ans.indexOf(rA);

            question.setId(id);
            question.setAks(aks);
            question.setRa(ans.get(0));
            question.setRb(ans.get(1));
            question.setRc(ans.get(2));
            question.setRd(ans.get(3));
            question.setLevel(level);
            question.setTrueCase(trueCase);
        }
        closeDatabase();
        return question;
    }

    public List<Question> query15Question() {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM ( SELECT * FROM question ORDER BY RANDOM() ) " +
                "GROUP BY level ORDER BY level ASC" + " LIMIT 15";
        openDatabase();

        Cursor cursor = mDatabase.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int indexID = cursor.getColumnIndex("id");
            int indexAsk = cursor.getColumnIndex("ask");
            int indexRa = cursor.getColumnIndex("ra");
            int indexRb = cursor.getColumnIndex("rb");
            int indexRc = cursor.getColumnIndex("rc");
            int indexRd = cursor.getColumnIndex("rd");
            int indexLevel = cursor.getColumnIndex("level");

            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(indexID);
                String aks = cursor.getString(indexAsk);
                String rA = cursor.getString(indexRa);
                String rB = cursor.getString(indexRb);
                String rC = cursor.getString(indexRc);
                String rD = cursor.getString(indexRd);
                int level = cursor.getInt(indexLevel);
                List<String> ans = new ArrayList<>();
                ans.add(rA);
                ans.add(rB);
                ans.add(rC);
                ans.add(rD);
                Collections.shuffle(ans);
                int trueCase = ans.indexOf(rA);

                Question question = new Question(id, aks,
                        ans.get(0), ans.get(1), ans.get(2), ans.get(3),
                                level, trueCase);
                questions.add(question);
                cursor.moveToNext();
            }
        }
        closeDatabase();
        return questions;
    }

    public void insertHighScore(String name, int levelPass, String money) {
        openDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("level_pass", levelPass);
        values.put("money", money);
        mDatabase.insert("hight_score", null, values);

        closeDatabase();
    }
}
