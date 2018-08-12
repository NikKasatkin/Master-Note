package com.example.ot28102016.seshmasternote110;

/**
 * Created by Nick on 13.05.2018.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;



public class DB {
    //DB info
    private static final String DB_NAME = "WorkSeshDbNew2";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "FerstTableNew2";
    private static final String DB_TABLEtc = "FerstTableNew3";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ORDERS = "orderS";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AMOUNT = "amount";

    public static final String COLUMN_TANKS = "tanks";
    public static final String COLUMN_COPES = "copes";



    //create DB
    private static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_ORDERS + " text, " +
                    COLUMN_NAME + " text, " +
                    COLUMN_AMOUNT + " text" +
                    ");";

    private static final String DB_CREATE_TanksCopes =
            "create table " + DB_TABLEtc + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_NAME + " text, " +
                    COLUMN_TANKS + " text, " +
                    COLUMN_COPES + " text" +
                    ");";

    //conect new DB
    private final Context mCtx;
    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context ctx) {
        mCtx = ctx;
    }

    // open DB
    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    // close DB
    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }

    // load all data from DB
    public Cursor getAllData() {
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }
    public Cursor getAllDataTC() {
        return mDB.query(DB_TABLEtc, null, null, null, null, null, null);
    }
    public Cursor getAllDataRES() {
        SQLiteDatabase mDB1 = mDBHelper.getWritableDatabase();
        String table = "FerstTableNew2 join FerstTableNew3 on FerstTableNew2.name = FerstTableNew3.name";
        String a = "FerstTableNew2.name";
        return mDB1.query(table, null, null, null, null, null, a);
    }

    public Cursor addDataToED(long _id){
        Cursor res = mDB.rawQuery("Select * from " + DB_TABLE + " WHERE _id = " + _id, null);
        return res;
    }
    public Cursor addDataToEDtc(long _id){
        Cursor res = mDB.rawQuery("Select * from " + DB_TABLEtc + " WHERE _id = " + _id, null);
        return res;
    }

    // load String on ID
    public Cursor getStringData(long _id){
        return mDB.rawQuery("Select * from " + DB_TABLE + " WHERE _id = " + _id, null);
    }
    public Cursor getStringDataTC(long _id){
        return mDB.rawQuery("Select * from " + DB_TABLEtc + " WHERE _id = " + _id, null);
    }

    // add data from DB
    public void addRec(String orderS, String name, String amount) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ORDERS, orderS);
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_AMOUNT, amount);
        mDB.insert(DB_TABLE, null, cv);
    }
    public void addRecTC(String name, String tanks, String copes) { //b.addRecTC(name,tanks,copes);
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_TANKS, tanks);
        cv.put(COLUMN_COPES, copes);
        mDB.insert(DB_TABLEtc, null, cv);
    }
    // update data from DB
    public void addUpdate(long _id, String orderS, String name, String amount) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ORDERS, orderS);
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_AMOUNT, amount);
        mDB.update(DB_TABLE, cv, COLUMN_ID + " = " + _id, null);
    }
    public void addUpdateTC(long _id, String name, String tanks, String copes) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_TANKS, tanks);
        cv.put(COLUMN_COPES, copes);
        mDB.update(DB_TABLEtc, cv, COLUMN_ID + " = " + _id, null);
    }

    // delete data from DB
    public void delRec(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }
    public void delRecTC(long id) {
        mDB.delete(DB_TABLEtc, COLUMN_ID + " = " + id, null);
    }

    // DataBase HELPER on first load DB
    public static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context, String name, CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        // add data on first load DB
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
            ContentValues cv = new ContentValues();
            for (int i = 1; i < 3; i++) {
                cv.put(COLUMN_ORDERS, "ORDERS");
                cv.put(COLUMN_NAME, "NAME");
                cv.put(COLUMN_AMOUNT, "AMOUNT");
                db.insert(DB_TABLE, null, cv);
            }

            db.execSQL(DB_CREATE_TanksCopes);
            ContentValues cv1 = new ContentValues();
            for (int i = 1; i < 3; i++) {
                cv1.put(COLUMN_NAME, "NAME");
                cv1.put(COLUMN_TANKS, "TANKS");
                cv1.put(COLUMN_COPES, "COPES");
                db.insert(DB_TABLEtc, null, cv1);
            }
        }



        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }


}

