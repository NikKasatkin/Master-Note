package com.example.ot28102016.seshmasternote110;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class TanksCopesAct extends AppCompatActivity {

    private static final int CM_DELETE_ID = 1;
    ListView lvData;
    DB db;
    SimpleCursorAdapter scAdapter1;
    Cursor cursor;
    private long _id;
    TextView tvName;
    String NameTrans;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanks_copes_db);

        tvName = (TextView) findViewById(R.id.tvName);

        Intent intent = getIntent();
        NameTrans = intent.getStringExtra("nid");
        tvName.setText(NameTrans);

        // open DB
        db = new DB(this);
        db.open();

        // take cursor
        cursor = db.getAllDataTC();

        // create ListView
        String[] from = new String[] { DB.COLUMN_NAME, DB.COLUMN_TANKS, DB.COLUMN_COPES };
        int[] to = new int[] { R.id.tvOneTC, R.id.tvTwoTC, R.id.tvThreeTC };

        // create adapter and ListView
        scAdapter1 = new SimpleCursorAdapter(this, R.layout.item_tc, cursor, from, to);
        lvData = (ListView) findViewById(R.id.lvDataTC);
        lvData.setAdapter(scAdapter1);

        // add menu
        registerForContextMenu(lvData);
    }

    // onClick
    public void onButtonClick(View view) {
        //goto AddActivityTC
        Intent modify_intent = new Intent(this, AddActivityTC.class);
        modify_intent.putExtra("nid", NameTrans);
        startActivity(modify_intent);
    }
    public void onButtonGoToHOMEsweetHome(View view) {
        //goto AddActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //create menu NOT working
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, "УДАЛИТЬ");
        menu.add(0, v.getId(), 0, "ИЗМЕНИТЬ");
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            // teke data from list
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            // take id from data and give id to DB
            db.delRecTC(acmi.id);
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
            Intent modify_intent = new Intent(getApplicationContext(), UpdateDBTC.class);
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
