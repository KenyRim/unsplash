package com.appdev.unsplash.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbMainContent {
    private long id;
    private int isChosen;
    private String pupDate,smallImageUrl, largeImageUrl;

    private static final int DATABASE_VERSION = 1;
    private final String DATABASE_NAME = "offline_v"+DATABASE_VERSION+".db";
    private static final String TABLE_NAME = "myTable";

    private static final String ID = "_id";
    private static final String COL_PUP_DATE   = "pupDate";
    private static final String COL_IMAGE_SMALL   = "smallImageUrl";
    private static final String COL_IMAGE_LARGE   = "largeImageUrl";
    private static final String COL_IS_CHOSEN   = "isChosen";

    private static final int NUM_ID = 0;
    private static final int NUM_PUP_DATE = 1;
    private static final int NUM_IMAGE_SMALL = 2;
    private static final int NUM_IMAGE_LARGE = 3;
    private static final int NUM_IS_CHOSEN   = 4;

    private SQLiteDatabase database;
    private Context context;

    public DbMainContent(Context context){
        this.context = context;
    }

    private class ResultOpenHelper extends SQLiteOpenHelper {
        private ResultOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            database = db;
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_PUP_DATE + " TEXT, " +
                    COL_IMAGE_SMALL + " TEXT, " +
                    COL_IMAGE_LARGE + " TEXT, " +
                    COL_IS_CHOSEN + " INTEGER, " +
                    "UNIQUE( " + COL_IMAGE_SMALL + " ) ON CONFLICT IGNORE);";
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    public void insert(ModelMain model) {
        open(context);
        ContentValues cv = new ContentValues();
        cv.put(COL_PUP_DATE    , model.getPupDate());
        cv.put(COL_IMAGE_SMALL    , model.getSmallImageUrl());
        cv.put(COL_IMAGE_LARGE    , model.getLargeImageUrl());
        cv.put(COL_IS_CHOSEN      , model.getIsChosen());
        database.insert(TABLE_NAME, null, cv);
        close();
    }

    public void update(long id, int flag){
        open(context);
        ContentValues newValues = new ContentValues();
        newValues.put(COL_IS_CHOSEN, flag);
        database.update(TABLE_NAME, newValues, ID + " = ?", new String[] {String.valueOf(id)});
        close();
    }

    public ArrayList<ModelMain> selectAll() {
        open(context);
        Cursor cursor = database.query
                (TABLE_NAME, null,null,null,null, null, COL_PUP_DATE + " DESC");
        ArrayList<ModelMain> arr = new ArrayList<>();
        cursor.moveToFirst();
        if (!cursor.isAfterLast()){
            do {
                id = cursor.getLong(NUM_ID);
                pupDate = cursor.getString(NUM_PUP_DATE);
                smallImageUrl = cursor.getString(NUM_IMAGE_SMALL);
                largeImageUrl = cursor.getString(NUM_IMAGE_LARGE);
                isChosen = cursor.getInt(NUM_IS_CHOSEN);

                arr.add(new ModelMain(id,pupDate, smallImageUrl, largeImageUrl, isChosen));
            }while (cursor.moveToNext());

        }
        cursor.close();
        close();
        return arr;
    }


    public ArrayList<ModelMain> selectAllChosen() {
        open(context);
        Cursor cursor = database.query
                (TABLE_NAME, null,null,null,null, null, ID);
        ArrayList<ModelMain> arr = new ArrayList<>();
        cursor.moveToFirst();
        if (!cursor.isAfterLast()){
            do {
                id = cursor.getLong(NUM_ID);
                pupDate = cursor.getString(NUM_PUP_DATE);
                smallImageUrl = cursor.getString(NUM_IMAGE_SMALL);
                largeImageUrl = cursor.getString(NUM_IMAGE_LARGE);
                isChosen = cursor.getInt(NUM_IS_CHOSEN);

                if (isChosen == 1) {
                    arr.add(new ModelMain(id,pupDate,smallImageUrl,largeImageUrl,isChosen));
                }

            }while (cursor.moveToNext());

        }
        cursor.close();
        close();
        return arr;
    }


    private void close() {
        database.close();
    }

    private void open(Context context){
        ResultOpenHelper openHelper = new ResultOpenHelper(context);
        database = openHelper.getWritableDatabase();
    }
}
