package com.cis4301.henry.assignment5;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.sqlite.*;
import android.database.Cursor;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.String;

import java.util.ArrayList;
import java.util.Queue;

public class flowerUpdate extends AppCompatActivity {
    ArrayList<String> commonNameList = new ArrayList<String>();
    SQLiteDatabase flowers_db;
    ListView myListViewFlower;
    EditText comN;
    EditText genus;
    EditText species;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comN=(EditText) findViewById(R.id.editComName);
        species=(EditText) findViewById(R.id.editSpecies);
        genus=(EditText) findViewById(R.id.editGenus);
        setContentView(R.layout.activity_flower_update);
        flowers_db = SQLiteDatabase.openDatabase("data/data/com.cis4301.henry.assignment5/databases/flowers.db",  null, 0 );
        myListViewFlower = (ListView) findViewById(R.id.list_view);
        String getComNames = "SELECT COMNAME FROM FLOWERS";

        final Cursor cursor = flowers_db.rawQuery(getComNames, null);

        if(cursor != null && cursor.moveToFirst())
            do{
                commonNameList.add(cursor.getString(0));
            } while(cursor.moveToNext());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, commonNameList);
        myListViewFlower.setAdapter(adapter);

        myListViewFlower.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapter,View v, int position, long id) {

                comN=(EditText) findViewById(R.id.editComName);
                species=(EditText) findViewById(R.id.editSpecies);
                genus=(EditText) findViewById(R.id.editGenus);

                genus.setClickable(true);
                genus.setFocusable(true);
                genus.setFocusableInTouchMode(true);
                genus.setCursorVisible(true);
                species.setClickable(true);
                species.setFocusable(true);
                species.setFocusableInTouchMode(true);
                species.setCursorVisible(true);

                String nameSelected = (String) myListViewFlower.getItemAtPosition(position);
                String quer= "SELECT GENUS,SPECIES FROM FLOWERS WHERE FLOWERS.COMNAME=="+"'"+nameSelected+"'";
               Cursor cursor2=flowers_db.rawQuery(quer, null);
               comN.setText(nameSelected, null);
               if(cursor2!=null && cursor2.moveToFirst()){
                   do{
                       genus.setText(cursor2.getString(0));
                       species.setText(cursor2.getString(1));
                   }while (cursor.moveToNext());
               }
               cursor2.close();


            }
        });
    }
    public void updateFlower(View view){
        if(comN.getText()==null){
            Toast.makeText(getApplicationContext(),"You have to select a Flower to edit",Toast.LENGTH_LONG);
            return;
        }
        String genusString = genus.getText().toString();
        if(genus.getText() != null && !genusString.contains("'") && !genusString.contains("\"")) {
            String updateQuery = "UPDATE FLOWERS " +
                    "SET GENUS = '" + this.genus.getText() + "'" +
                    "WHERE COMNAME = '" + comN.getText() + "'";
            Cursor cursor = flowers_db.rawQuery(updateQuery, null);
            cursor.moveToFirst();
            System.out.println("Updated genus for: " + comN.getText());
            cursor.close();

        }
        String speciesString = species.getText().toString();
        if(species.getText() != null && !speciesString.contains("'") && !speciesString.contains("\"")) {
            String updateQuery = "UPDATE FLOWERS " +
                    "SET SPECIES = '" + species.getText() + "'" +
                    "WHERE COMNAME = '" + comN.getText() + "'";
            Cursor cursor = flowers_db.rawQuery(updateQuery, null);
            cursor.moveToFirst();
            System.out.println("Updated species for: " + comN.getText());
            cursor.close();
        }
    }
    public void returnOnClick(View view){
        finish();
    }

}
