package com.example.ot28102016.seshmasternote110;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivityTC extends Activity {

    DB db;
    DBautoComplServ dbN;
    Cursor cursor;
    AutoCompleteTextView etname, ettanks, etcopes;
    String NameTrans;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tc);

        Intent intent = getIntent();
        NameTrans = intent.getStringExtra("nid");

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

        etname = (AutoCompleteTextView) findViewById(R.id.etNameTC);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,test1);
        etname.setAdapter(adapter);
        etname.setText(NameTrans);


        ettanks = (AutoCompleteTextView) findViewById(R.id.etTanks);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,test1);
        ettanks.setAdapter(adapter1);

        etcopes = (AutoCompleteTextView) findViewById(R.id.etCopes);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,test1);
        etcopes.setAdapter(adapter2);

        // open DB
        db = new DB(this);
        db.open();

        // take cursor
        cursor = db.getAllData();
    }

    // onClick
    public void onButtonClick(View view) {
        String name = etname.getText().toString();
        String tanks = ettanks.getText().toString();
        String copes = etcopes.getText().toString();
        // add info
        db.addRecTC(name,tanks,copes);
        //goto main
        Intent intent = new Intent(this, TanksCopesAct.class);
        startActivity(intent);
    }

    //close DB
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
    //onClick clear
    public void onButtonClaer(View view) {
        etname.setText(NameTrans);
        ettanks.setText(null);
        etcopes.setText(null);
    }

    // add T9
    public void onButtonT9(View view){
        Intent intent = new Intent(this, DBautoCompl.class);
        startActivity(intent);
    }
}
