package com.example.ot28102016.seshmasternote110;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class UpdateDBTC extends AppCompatActivity {

    ListView lvUpOutTC;
    DB db;
    SimpleCursorAdapter scAdapter;
    Cursor cursor, cursor1;
    TextView tvOne;
    EditText etUpname, etUptanks, etUpcopes;
    private long _id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_dbtc);

        etUpname = (EditText) findViewById(R.id.etUPName);
        etUptanks = (EditText) findViewById(R.id.etUPTanks);
        etUpcopes = (EditText) findViewById(R.id.etUPCopes);

        //take ID from MainActivity
        Intent intent = getIntent();
        String id = intent.getStringExtra("nid");
        _id = Long.parseLong(id);

        // open conect DB
        db = new DB(this);
        db.open();

        // take cursor
        cursor = db.getStringDataTC(_id);

        //create one string from DB on need id(nid)
        String[] from = new String[] { DB.COLUMN_NAME, DB.COLUMN_TANKS, DB.COLUMN_COPES };
        int[] to = new int[] { R.id.tvOneTC, R.id.tvTwoTC, R.id.tvThreeTC };

        // create listview
        scAdapter = new SimpleCursorAdapter(this, R.layout.item_tc, cursor, from, to);
        lvUpOutTC = (ListView) findViewById(R.id.lvUpOutTC);
        lvUpOutTC.setAdapter(scAdapter);

        // add String data to edit test space
        Cursor res = db.addDataToEDtc(_id);
        if (res!=null && res.getCount()>0) {
            while (res.moveToNext()) {
                etUpname.append(res.getString(1));
                etUptanks.setText(res.getString(2));
                etUpcopes.setText(res.getString(3));
            }
        }
    }


    //onClick update DATA
    public void onUpButtonClickTC(View view) { //String name, String tanks, String copes
        String name = etUpname.getText().toString();
        String tanks = etUptanks.getText().toString();
        String copes = etUpcopes.getText().toString();
        // update data from DB on id
        db.addUpdateTC(_id, name, tanks, copes);
        //goto MainActivity
        Intent intent = new Intent(this, TanksCopesAct.class);
        startActivity(intent);
    }

    protected void onDestroy() {
        super.onDestroy();
        // close DB
        db.close();
    }

    //onClick Clear
    public void onUpButtonClaerTC(View view) {
        etUpname.setText(null);
        etUptanks.setText(null);
        etUpcopes.setText(null);
    }
}
