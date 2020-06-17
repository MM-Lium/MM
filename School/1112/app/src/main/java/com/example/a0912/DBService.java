package com.example.a0912;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBService extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "money_database";

    public static final String TABLE_1 = "notes";
    public static final String ID = "money_id";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String TIME = "time";
    public static final String YEAR = "year";
    public static final String DAY = "day";
    public static final String DAY2 = "day2";
    public static final String MONEY = "money";
    public static final String LASTMONEY = "money2";
    public static final String PAPA = "papa";
    public static final String TYPE = "type";

    public static final String TABLE_2 = "needs";
    public static final String ID2 = "main_id";
    public static final String HYEAR2 = "hyear";
    public static final String HMONEY2 = "hmoney";
    public static final String NEED = "need";

    public static final String TABLE_3 = "nows";
    public static final String ID3 = "now_id";

    public static final String TABLE_4 = "internetional";
    public static final String ID4 = "outside_id";
    public static final String NUM = "num";
    public static final String NAME = "name";
    public static final String TOTAL = "total";

    public static final String Currency = "currency";
    public static final String Rate = "rate";

    private static final int VERSION = 5;//資料庫版本由1改為2

    private static final String CREATE_TABLE_ONE = "CREATE TABLE " + TABLE_1 + "( " + ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TITLE + " VARCHAR(30) ," +
            CONTENT + " TEXT , " +
            TYPE + " TEXT , " +
            MONEY + " DOUBLE , " +
            LASTMONEY + " DOUBLE , " +
            PAPA + " DOUBLE , " +
            TIME + " DATETIME NOT NULL," +
            DAY + " DATETIME NOT NULL," +
            DAY2 + " DATETIME NOT NULL," +
            YEAR + " DATETIME NOT NULL  , " +
            TOTAL + " DOUBLE," +
            NAME + " TEXT " + ")";

    private static final String CREATE_TABLE_TWO = "CREATE TABLE " + TABLE_2 + "( " + ID2 +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            HYEAR2 + " DATETIME NOT NULL," +
            HMONEY2 + " TEXT , " +
            NEED + " INT " + ")";

    private static final String CREATE_TABLE_THREE = "CREATE TABLE " + TABLE_3 + "( " + ID3 +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TITLE + " VARCHAR(30) ," +
            TYPE + " TEXT , " +
            Currency + " TEXT , " +
            CONTENT + " TEXT , " +
            Rate + " DOUBLE , " +
            MONEY + " DOUBLE , " +
            DAY + " DATETIME NOT NULL," +
            TIME + " DATETIME NOT NULL," +
            PAPA + " DOUBLE , " +
            TOTAL + " DOUBLE," +
            LASTMONEY + " DOUBLE " + ")";

    private static final String CREATE_TABLE_FOUR = "CREATE TABLE " + TABLE_4 + "( " + ID4 +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TITLE + " VARCHAR(30) ," +
            TYPE + " TEXT , " +
            CONTENT + " TEXT , " +
            MONEY + " DOUBLE , " +
            LASTMONEY + " DOUBLE , " +
            NAME + " TEXT , " +
            PAPA + " DOUBLE , " +
            TIME + " DATETIME NOT NULL," +
            DAY + " DATETIME NOT NULL," +
            DAY2 + " DATETIME NOT NULL," +
            NUM + " DOUBLE," +
            TOTAL + " DOUBLE," +
            YEAR + " DATETIME NOT NULL " + ")";

    public DBService(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ONE);
        db.execSQL(CREATE_TABLE_TWO);
        db.execSQL(CREATE_TABLE_THREE);
        db.execSQL(CREATE_TABLE_FOUR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_4);
        onCreate(db);

    }

    public Cursor viewData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM needs order by main_id DESC limit 1", null);
        return cursor;
    }

    public Cursor sum() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(money2) FROM notes", null);
        return cursor;
    }

    public Cursor sum2() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(money2) FROM internetional", null);
        return cursor;
    }

    public Cursor sum3() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(money2) FROM nows", null);
        return cursor;
    }

    public Cursor date() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT day2  FROM notes UNION SELECT day2  FROM internetional ", null);
        return cursor;
    }


    public Cursor totallyT() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name,SUM(money) FROM notes UNION SELECT name,SUM(money2)FROM internetional  group by name", null);
        return cursor;
    }
}