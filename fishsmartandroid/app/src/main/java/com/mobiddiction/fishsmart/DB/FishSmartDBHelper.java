package com.mobiddiction.fishsmart.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Archa on 7/01/2016.
 */
public class FishSmartDBHelper extends SQLiteOpenHelper {

    public FishSmartDBHelper(Context context)
    {
        super (context, "fishsmart.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE RULESAVE("+ RuleSaveEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                               + RuleSaveEntry.FISHTYPEID + " TEXT, "
                                               + RuleSaveEntry.TYPE + " TEXT, "
                                               + RuleSaveEntry.TITLE + " TEXT, "
                                               + RuleSaveEntry.DESCRIPTION + " TEXT, "
                                               + RuleSaveEntry.PDFURL + " TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS RULESAVE");
        onCreate(db);
    }
}