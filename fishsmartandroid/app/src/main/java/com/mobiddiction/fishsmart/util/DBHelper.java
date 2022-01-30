package com.mobiddiction.fishsmart.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 33;
    private Context context;

    public DBHelper(Context context) {
        super(context, Config.DB_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public SQLiteDatabase execute() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransactionNonExclusive();
        return db;
    }

    public void execute(String statment) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Log.print(this.getClass() + " :: execute() :: ", statment);
            db.execSQL(statment);
        } catch (Exception e) {
            Log.error(this.getClass() + " :: execute() ::", e);
        } finally {
            db.close();
            db = null;
        }
    }

    public Cursor query(String statment) {
        Cursor cur = null;
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Log.print(this.getClass() + " :: query() :: ", statment);
            cur = db.rawQuery(statment, null);
            cur.moveToPosition(-1);
        } catch (Exception e) {
            Log.error(this.getClass() + " :: query() ::", e);
        } finally {
            db.close();
            db = null;
        }
        return cur;
    }

    public static String getDBStr(String str) {
        str = str != null ? str.replaceAll("'", "''") : null;
        str = str != null ? str.replaceAll("&#039;", "''") : null;
        str = str != null ? str.replaceAll("&amp;", "&") : null;
        return str;
    }

    public static String getDBStrforText(String str) {
        str = str != null ? str.replaceAll("''", "'") : null;
        str = str != null ? str.replaceAll("&#039;", "'") : null;
        str = str != null ? str.replaceAll("&amp;", "&") : null;
        return str;
    }

    public void upgrade(int level) {
        switch (level) {
            case 0:
                doUpdate0();
                break;
        }
    }

    private void doUpdate0() {
        this.execute("CREATE TABLE IF NOT EXISTS catchlog (id INTEGER PRIMARY KEY AUTOINCREMENT, myjson TEXT)");
    }
}