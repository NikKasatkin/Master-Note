package com.example.ot28102016.seshmasternote110;

/**
 * Created by ot28102016 on 03.06.2018.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class DBautoComplServ {
    //DB info
    private static final String DB_NAME = "AutoCompleteServes1";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "OneTable1";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ONE = "COLUMN1";


    //create DB
    private static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_ONE + " text" +
                    ");";


    //conect new DB
    private final Context mCtx;
    private DBautoComplServ.DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DBautoComplServ(Context ctx) {
        mCtx = ctx;
    }

    // open DB
    public void open() {
        mDBHelper = new DBautoComplServ.DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    // close DB
    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }


    // load all data from DB
    public Cursor getAllData1() {
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }

    // load String on ID
    public Cursor getStringAllData(){
        Cursor res =  mDB.rawQuery("Select * from " + DB_TABLE, null);
        return res;
    }

    // add data from DB
    public void addRec(String COLUMN1) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ONE, COLUMN1);
        mDB.insert(DB_TABLE, null, cv);
    }

    // delete data from DB
    public void delRec(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }

    // DataBase HELPER on first load DB
    private class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context, String name, CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        // add data on first load DB
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
            ContentValues cv = new ContentValues();
            for (int i = 1; i < 3; i++) {
                cv.put(COLUMN_ONE, "COLUMN1");
                db.insert(DB_TABLE, null, cv);
            }
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}

