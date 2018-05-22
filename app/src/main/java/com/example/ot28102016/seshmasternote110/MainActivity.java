package com.example.ot28102016.seshmasternote110;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.content.Intent;

public class MainActivity extends Activity {

    private static final int CM_DELETE_ID = 1;
    ListView lvData;
    DB db;
    SimpleCursorAdapter scAdapter;
    Cursor cursor;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            // open DB
        db = new DB(this);
        db.open();

            // take cursor
        cursor = db.getAllData();

            // create ListView
        String[] from = new String[] { DB.COLUMN_ORDERS, DB.COLUMN_NAME, DB.COLUMN_AMOUNT };
        int[] to = new int[] { R.id.tvOne, R.id.tvTwo, R.id.tvThree };

            // create adapter and ListView
        scAdapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to);
        lvData = (ListView) findViewById(R.id.lvData);
        lvData.setAdapter(scAdapter);

            // add menu
        registerForContextMenu(lvData);
    }

    // onClick
    public void onButtonClick(View view) {
            //goto AddActivity
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    //create menu
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, "УДАЛИТЬ");
        menu.add(0, v.getId(), 0, "ИЗМЕНИТЬ");
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
                // teke data from list
            AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
                // take id from data and give id to DB
            db.delRec(acmi.id);
            cursor.requery();
            return true;
        }else if (item.getTitle()=="ИЗМЕНИТЬ"){
                // teke data from list
            AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
                // take id from data and give id to "long _id"
            long _id = acmi.id;
                //long _id update from String id
            String id = String.valueOf(_id);
                //give id in Update.class
            Intent modify_intent = new Intent(getApplicationContext(), UpdateDB.class);
            modify_intent.putExtra("nid", id);
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

