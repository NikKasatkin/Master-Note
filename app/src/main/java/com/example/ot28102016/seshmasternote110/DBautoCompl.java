package com.example.ot28102016.seshmasternote110;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class DBautoCompl extends AppCompatActivity {

    private static final int CM_DELETE_ID = 1;
    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView2;
    DBautoComplServ dbN;
    Cursor cursor;
    SimpleCursorAdapter scAdapter;
    ListView lvDataT9;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbauto_compl);

        // open DB
        dbN = new DBautoComplServ(this);
        dbN.open();

        // take cursor
        cursor = dbN.getAllData1();

        Cursor res = dbN.getStringAllData();
        StringBuffer stringBuffer = new StringBuffer();
        if (res!=null && res.getCount()>0){

            while (res.moveToNext()){
                stringBuffer.append(res.getString(1)+" ");
                //
            }
        }else{
            Toast.makeText(this,"JUST DO IT...",Toast.LENGTH_LONG).show();
        }
        String add = stringBuffer.toString();
        String [] test1 = add.split(" ");

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,test1);
        autoCompleteTextView.setAdapter(adapter);

        cursor = dbN.getAllData1();
        // create ListView
        String[] from = new String[] { DBautoComplServ.COLUMN_ONE };
        int[] to = new int[] { R.id.tv9 };

        // create adapter and ListView
        scAdapter = new SimpleCursorAdapter(this, R.layout.item9, cursor, from, to);
        lvDataT9 = (ListView) findViewById(R.id.lvDataT9);
        lvDataT9.setAdapter(scAdapter);

        // add menu
        registerForContextMenu(lvDataT9);
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, "УДАЛИТЬ");
    }

    public void onButtonClickADD(View view) {
        String COLUMN1 = autoCompleteTextView.getText().toString();
        // add info
        dbN.addRec(COLUMN1);
        //goto main
        Intent intent = new Intent(this, DBautoCompl.class);
        startActivity(intent);
    }

    protected void onDestroy() {
        super.onDestroy();
        dbN.close();
    }

    public void onButtonClickMAINGO(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            // teke data from list
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            // take id from data and give id to DB
            dbN.delRec(acmi.id);
            cursor.requery();
            return true;
        }

        return super.onContextItemSelected(item);
    }



}