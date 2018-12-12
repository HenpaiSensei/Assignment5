package com.cis4301.henry.assignment5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.sqlite.*;
import android.database.Cursor;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView myListView;
    ArrayList<String> commonNameList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase flowers_db = SQLiteDatabase.openDatabase("data/data/com.cis4301.henry.assignment5/databases/flowers.db",  null, 0 );
        myListView = (ListView) findViewById(R.id.list_view);

        String getComNames = "SELECT COMNAME FROM FLOWERS";

        Cursor cursor = flowers_db.rawQuery(getComNames, null);

        if(cursor != null && cursor.moveToFirst())
            do{
            commonNameList.add(cursor.getString(0));
         } while(cursor.moveToNext());

         ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, commonNameList);
        myListView.setAdapter(adapter);



    }
}
