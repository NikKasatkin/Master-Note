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

public class UpdateDB extends AppCompatActivity {

    ListView lvUpOut;
    DB db;
    SimpleCursorAdapter scAdapter;
    Cursor cursor, cursor1;
    TextView tvOne;
    EditText etUporderS, etUpname, etUpamount;
    private long _id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_db);

        etUporderS = (EditText) findViewById(R.id.etUPOrder);
        etUpname = (EditText) findViewById(R.id.etUPName);
        etUpamount = (EditText) findViewById(R.id.etUPAmount);

        //take ID from MainActivity
        Intent intent = getIntent();
        String id = intent.getStringExtra("nid");
        _id = Long.parseLong(id);

        // open conect DB
        db = new DB(this);
        db.open();

        // take cursor
        cursor = db.getStringData(_id);

        //create one string from DB on need id(nid)
        String[] from = new String[] { DB.COLUMN_ORDERS, DB.COLUMN_NAME, DB.COLUMN_AMOUNT };
        int[] to = new int[] { R.id.tvOne, R.id.tvTwo, R.id.tvThree };

        // create listview
        scAdapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to);
        lvUpOut = (ListView) findViewById(R.id.lvUpOut);
        lvUpOut.setAdapter(scAdapter);

        // add String data to edit test space
        Cursor res = db.addDataToED(_id);
        if (res!=null && res.getCount()>0) {
            while (res.moveToNext()) {
                etUporderS.append(res.getString(1));
                etUpname.setText(res.getString(2));
                etUpamount.setText(res.getString(3));
            }
        }
    }


    //onClick update DATA
    public void onUpButtonClick(View view) {
        String orderS = etUporderS.getText().toString();
        String name = etUpname.getText().toString();
        String amount = etUpamount.getText().toString();
        // update data from DB on id
        db.addUpdate(_id, orderS, name, amount);
        //goto MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    protected void onDestroy() {
        super.onDestroy();
        // close DB
        db.close();
    }

    //onClick Clear
    public void onUpButtonClaer(View view) {
        etUporderS.setText(null);
        etUpname.setText(null);
        etUpamount.setText(null);
    }
}

