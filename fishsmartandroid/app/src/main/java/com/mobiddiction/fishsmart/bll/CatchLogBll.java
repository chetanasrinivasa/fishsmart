package com.mobiddiction.fishsmart.bll;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mobiddiction.fishsmart.bean.CatchLogBean;
import com.mobiddiction.fishsmart.util.DBHelper;
import com.mobiddiction.fishsmart.util.Log;
import com.mobiddiction.fishsmart.util.Utils;

import java.util.ArrayList;

/**
 * Created by Krunal on 06/12/18.
 */
public class CatchLogBll {
    public Context context;

    public CatchLogBll(Context context) {
        this.context = context;
    }

    public void verify(CatchLogBean catchLogBean) {
        DBHelper dbHelper = null;
        String sql;
        Cursor cursor = null;

        try {
            sql = "SELECT id FROM catchlog WHERE id =" + catchLogBean.id;
            dbHelper = new DBHelper(this.context);
            cursor = dbHelper.query(sql);

            if (cursor != null && cursor.getCount() == 0) {
                this.insert(catchLogBean);
            } else {
                this.update(catchLogBean);
            }
        } catch (Exception e) {
            Log.error(this.getClass() + " :: verify()", e);
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();

            if (dbHelper != null)
                dbHelper.close();

            Utils.freeMemory();
        }
    }

    public void insert(CatchLogBean catchLogBean) {
        String sql;
        SQLiteDatabase db = null;
        SQLiteStatement stmt;

        try {
            sql = "INSERT INTO catchlog (myjson) VALUES (?)";
            db = new DBHelper(context).execute();
            stmt = db.compileStatement(sql);

            Log.print(this.getClass() + " :: insert() :: ", sql);
            stmt.bindString(1, catchLogBean.myJson.trim());
            stmt.execute();
            stmt.clearBindings();

            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception e) {
            Log.error(this.getClass() + " :: insert()", e);
        } finally {
            // release
            if (db != null)
                db.close();
            Utils.freeMemory();
        }
    }

    public void update(CatchLogBean catchLogBean) {
        String sql;
        SQLiteDatabase db = null;
        SQLiteStatement stmt;

        try {
            sql = "UPDATE catchlog SET myjson=? WHERE id=?";
            db = new DBHelper(context).execute();
            stmt = db.compileStatement(sql);

            System.out.println("========= ID ======" + catchLogBean.id);

            stmt.bindString(1, catchLogBean.myJson);
            stmt.bindString(2, "" + catchLogBean.id);

            stmt.execute();
            stmt.clearBindings();

            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception e) {
            Log.error(this.getClass() + " :: update()", e);
        } finally {
            // release
            if (db != null)
                db.close();
            Utils.freeMemory();
        }
    }

    public void deleteCatchLog(int id) {
        DBHelper dbHelper = null;
        String sql;

        try {
            dbHelper = new DBHelper(this.context);
            sql = "DELETE FROM catchlog WHERE id=" + id;
            dbHelper.execute(sql);
        } catch (Exception e) {
            Log.error(this.getClass() + " :: deleteCatchLog()", e);
            Log.print(this.getClass() + " :: deleteCatchLog()", " " + e);
        } finally {
            if (dbHelper != null)
                dbHelper.close();
            Utils.freeMemory();
        }
    }

    public void deleteAllCatchLog() {
        DBHelper dbHelper = null;
        String sql;

        try {
            dbHelper = new DBHelper(this.context);
            sql = "DELETE FROM catchlog";
            dbHelper.execute(sql);
        } catch (Exception e) {
            Log.error(this.getClass() + " :: deleteAllCatchLog()", e);
            Log.print(this.getClass() + " :: deleteAllCatchLog()", " " + e);
        } finally {
            if (dbHelper != null)
                dbHelper.close();
            Utils.freeMemory();
        }
    }

    public CatchLogBean getCatchLog(int id) {
        DBHelper dbHelper = null;
        String sql;
        Cursor cursor = null;
        CatchLogBean catchLogBean = null;

        try {
            sql = "SELECT * FROM catchlog WHERE id=" + id;
            dbHelper = new DBHelper(this.context);
            cursor = dbHelper.query(sql);

            catchLogBean = new CatchLogBean();
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    catchLogBean.id = cursor.getInt(0);
                    catchLogBean.myJson = cursor.getString(2);
                }
            }
        } catch (Exception e) {
            Log.error(this.getClass() + " :: getCatchLog()", e);
            Log.print(this.getClass() + " :: getCatchLog()", " " + e);
        } finally {
            // release
            if (cursor != null && !cursor.isClosed())
                cursor.close();

            if (dbHelper != null)
                dbHelper.close();

            Utils.freeMemory();
        }
        return catchLogBean;
    }

    public ArrayList<CatchLogBean> getCatchLogList() {
        DBHelper dbHelper = null;
        String sql;
        Cursor cursor = null;
        CatchLogBean catchLogBean;
        ArrayList<CatchLogBean> catchLogList = null;

        try {
            sql = "SELECT * FROM catchlog";
            dbHelper = new DBHelper(this.context);
            cursor = dbHelper.query(sql);

            catchLogList = new ArrayList<>();
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    catchLogBean = new CatchLogBean();
                    catchLogBean.id = cursor.getInt(0);
                    catchLogBean.myJson = cursor.getString(1);
                    System.out.println("=========== ID ========" + catchLogBean.id);
                    System.out.println("=========== JSON ========" + catchLogBean.myJson);
                    catchLogList.add(catchLogBean);
                }
            }
        } catch (Exception e) {
            Log.error(this.getClass() + " :: getCatchLogList()", e);
            Log.print(this.getClass() + " :: getCatchLogList()", " " + e);
        } finally {
            // release
            if (cursor != null && !cursor.isClosed())
                cursor.close();

            if (dbHelper != null)
                dbHelper.close();

            Utils.freeMemory();
        }
        return catchLogList;
    }

    public int getNextId() {
        DBHelper dbHelper = null;
        String sql;
        Cursor cursor = null;
        int maxId = 0;

        try {
            sql = "SELECT * FROM catchlog WHERE id = (SELECT MAX(ID) FROM catchlog)";
            dbHelper = new DBHelper(this.context);
            cursor = dbHelper.query(sql);

            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    maxId = cursor.getInt(0);
                    System.out.println("====== MAX ID IN QUERY =======" + maxId);
                }
            }
        } catch (Exception e) {
            Log.error(this.getClass() + " :: getNextId()", e);
            Log.print(this.getClass() + " :: getNextId()", " " + e);
        } finally {
            // release
            if (cursor != null && !cursor.isClosed())
                cursor.close();

            if (dbHelper != null)
                dbHelper.close();

            Utils.freeMemory();
        }
        return maxId;
    }
}