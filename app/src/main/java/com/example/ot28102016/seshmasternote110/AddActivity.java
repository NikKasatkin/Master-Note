package com.example.ot28102016.seshmasternote110;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends Activity {

    DB db;
    DBautoComplServ dbN;
    Cursor cursor;
    AutoCompleteTextView etorderS, etname, etamount;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

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
            //tvSEE.setText(stringBuffer.toString());
        }else{
            Toast.makeText(this,"JUST DO IT ...",Toast.LENGTH_LONG).show();
        }
        String add = stringBuffer.toString();
        String [] test1 = add.split(" ");

        etorderS = (AutoCompleteTextView) findViewById(R.id.etOrder);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,test1);
        etorderS.setAdapter(adapter);

        etname = (AutoCompleteTextView) findViewById(R.id.etName);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,test1);
        etname.setAdapter(adapter1);

        etamount = (AutoCompleteTextView) findViewById(R.id.etAmount);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,test1);
        etamount.setAdapter(adapter2);

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
    //go to Main
    public void onButtonGoMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // add T9
    public void onButtonT9(View view){
        Intent intent = new Intent(this, DBautoCompl.class);
        startActivity(intent);
    }
}

