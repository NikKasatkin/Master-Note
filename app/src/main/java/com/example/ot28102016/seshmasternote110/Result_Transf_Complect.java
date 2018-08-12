package com.example.ot28102016.seshmasternote110;

//out information from Activ(FerstTableNew2), TC(FerstTableNew3)

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;

public class Result_Transf_Complect extends AppCompatActivity {

    private static final int CM_DELETE_ID = 1;
    ListView lvData;
    DB db;
    SimpleCursorAdapter scAdapter1;
    public Cursor cursor;
    String NameTrans;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result__transf__complect);

        db = new DB(this);
        db.open();

        // open DB
        db = new DB(this);        db.open();

        cursor = db.getAllDataRES();

        // take cursor
        //cursor = db.getAllDataRES();

        // create ListView
        String[] from = new String[] { DB.COLUMN_ORDERS, DB.COLUMN_NAME, DB.COLUMN_AMOUNT, DB.COLUMN_TANKS, DB.COLUMN_COPES};
        int[] to = new int[] { R.id.tvOneRes, R.id.tvTwoRes, R.id.tvThreeRes, R.id.tvFourRes, R.id.tvFiveRes };

        // create adapter and ListView
        scAdapter1 = new SimpleCursorAdapter(this, R.layout.item_result, cursor, from, to);
        lvData = (ListView) findViewById(R.id.lvDataOutResult);
        lvData.setAdapter(scAdapter1);

        // add menu
        registerForContextMenu(lvData);
    }

    // onClick
    public void onButtonClickGoMain(View view) {
        //goto AddActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onButtonClickGoTC(View view) {
        //goto AddActivity
        Intent intent = new Intent(this, TanksCopesAct.class);
        startActivity(intent);
    }


    //create menu
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, "УДАЛИТЬ");
        menu.add(0, v.getId(), 0, "ИЗМЕНИТЬ");
        menu.add(0, v.getId(), 0, "добавить бак и крышку");
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            // teke data from list
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            // take id from data and give id to DB
            db.delRec(acmi.id);
            cursor.requery();
            return true;
        }else if (item.getTitle()=="ИЗМЕНИТЬ"){
            // teke data from list
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            // take id from data and give id to "long _id"
            long _id = acmi.id;
            //long _id update from String id
            String id = String.valueOf(_id);
            //give id in Update.class
            Intent modify_intent = new Intent(getApplicationContext(), UpdateDB.class);
            modify_intent.putExtra("nid", id);
            startActivity(modify_intent);
        }else if (item.getTitle()=="добавить бак и крышку"){
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            long _id = acmi.id;
            Cursor res = db.addDataToED(_id);
            if (res!=null && res.getCount()>0) {
                while (res.moveToNext()) {
                    NameTrans = res.getString(2);
                }
            }
            Intent modify_intent = new Intent(getApplicationContext(), TanksCopesAct.class);
            modify_intent.putExtra("nid", NameTrans);
            startActivity(modify_intent);
        }
        return super.onContextItemSelected(item);
    }

    protected void onDestroy() {
        super.onDestroy();
        // close DB
        db.close();
    }
}
