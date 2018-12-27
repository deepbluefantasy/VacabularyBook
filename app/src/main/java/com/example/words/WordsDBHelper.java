package com.example.words;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.words.wordcontract.Words;
public class WordsDBHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "wordsdb";
    private final static int DATABASE_VERSION = 1;



    private final static String SQL_CREATE_DATABASE = "CREATE TABLE " + Words.Word.TABLE_NAME + " (" +
            Words.Word._ID + " VARCHAR(32) PRIMARY KEY NOT NULL," +
            Words.Word.COLUMN_NAME_WORD + " TEXT UNIQUE NOT NULL,"+
            Words.Word.COLUMN_NAME_MEANING + " TEXT,"
            + Words.Word.COLUMN_NAME_SAMPLE + " TEXT)";

    private final static String SQL_DELETE_DATABASE = "DROP TABLE IF EXISTS " + Words.Word.TABLE_NAME;

    public WordsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_DATABASE);
        onCreate(sqLiteDatabase);
    }
}
