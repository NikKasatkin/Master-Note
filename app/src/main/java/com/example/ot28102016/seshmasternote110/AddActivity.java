package com.example.ot28102016.seshmasternote110;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.View;
import android.widget.EditText;

public class AddActivity extends Activity {

    DB db;
    Cursor cursor;
    EditText etorderS, etname, etamount;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etorderS = (EditText) findViewById(R.id.etOrder);
        etname = (EditText) findViewById(R.id.etName);
        etamount = (EditText) findViewById(R.id.etAmount);

            // open DB
        db = new DB(this);
        db.open();

            // take cursor
        cursor = db.getAllData();
    }

    // onClick
    public void onButtonClick(View view) {
        String orderS = etorderS.getText().toString();
        String name = etname.getText().toString();
        String amount = etamount.getText().toString();
            // add info
        db.addRec(orderS,name,amount);
            //goto main
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //close DB
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
    //onClick clear
    public void onButtonClaer(View view) {
        etorderS.setText(null);
        etname.setText(null);
        etamount.setText(null);
    }
}

