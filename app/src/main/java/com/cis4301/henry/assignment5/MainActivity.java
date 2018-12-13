package com.cis4301.henry.assignment5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.sqlite.*;
import android.database.Cursor;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import java.lang.String;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView myListViewFlower;
    ListView myListViewSighting;

    ArrayList<String> commonNameList = new ArrayList<String>();
    SQLiteDatabase flowers_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flowers_db = SQLiteDatabase.openDatabase("data/data/com.cis4301.henry.assignment5/databases/flowers.db",  null, 0 );
        myListViewFlower = (ListView) findViewById(R.id.list_view);
        myListViewSighting=(ListView) findViewById(R.id.listviewSigthings);

        //createIndexes(); Only run one time

        String getComNames = "SELECT COMNAME FROM FLOWERS";

        Cursor cursor = flowers_db.rawQuery(getComNames, null);

        if(cursor != null && cursor.moveToFirst())
            do{
            commonNameList.add(cursor.getString(0));
         } while(cursor.moveToNext());

         ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, commonNameList);
        myListViewFlower.setAdapter(adapter);
        
        myListViewFlower.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapter,View v, int position, long id){

                String nameSelected = (String) myListViewFlower.getItemAtPosition(position);
                ArrayList<String> correspondingSightings = getSightings(nameSelected);
                
            }
        });


    }

    ArrayList<String> getSightings(String nameSelected){
        ArrayList<String> sightingsList = new ArrayList<String>();
        System.out.println(nameSelected);
        String getSightingsQuery = "SELECT PERSON, LOCATION, SIGHTED" +
                " FROM SIGHTINGS, FLOWERS" +
                " WHERE " + "FLOWERS.COMNAME = SIGHTINGS.NAME" + " AND " +  "FLOWERS.COMNAME" + "=" + "'" + nameSelected + "'" +
                " ORDER BY SIGHTED DESC" +
                " LIMIT 10";

        Cursor cursor = flowers_db.rawQuery(getSightingsQuery, null);
        int i = 0;
        if(cursor != null && cursor.moveToFirst())
            do{
                sightingsList.add(cursor.getString(2) + ": " + cursor.getString(0) + " saw this flower at " + cursor.getString(1));
                System.out.println(sightingsList.get(i++));
            } while(cursor.moveToNext());
        ArrayAdapter<String> adapt= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,sightingsList);
        myListViewSighting.setAdapter(adapt);

        return sightingsList;

    }



    void insertSighting(String flower_name, String person_name, String location, String date_sighted){
        String insertQuery = "INSERT INTO SIGHTINGS (NAME, PERSON, LOCATION, SIGHTED)" +
                "VALUES ('" + flower_name + "', '" + person_name + "','" + location + "','" + date_sighted + "')";
        Cursor cursor = flowers_db.rawQuery(insertQuery, null);
        cursor.moveToFirst();
        cursor.close();
    }

    public void openUpdateFlower(View view){
        Intent i= new Intent(getBaseContext(),flowerUpdate.class);
        startActivity(i);
    }
    public void openInsertSighting(View view){
        Intent i= new Intent(getBaseContext(),InsertSightings.class);
        startActivity(i);
    }

    void createIndexes(){
        String nameIndex = "CREATE INDEX name_index ON SIGHTINGS (NAME)";
        String personIndex = "CREATE INDEX person_index  ON SIGHTINGS (PERSON)";
        String locationIndex = "CREATE INDEX location_index ON SIGHTINGS (LOCATION)";
        String sightedIndex = "CREATE INDEX sighted_index ON SIGHTINGS (SIGHTED)";

        Cursor nameCursor = flowers_db.rawQuery(nameIndex, null);
            nameCursor.moveToFirst();
            nameCursor.close();

        Cursor personCursor = flowers_db.rawQuery(personIndex, null);
        personCursor.moveToFirst();
        personCursor.close();

        Cursor locationCursor = flowers_db.rawQuery(locationIndex, null);
        locationCursor.moveToFirst();
        locationCursor.close();

        Cursor sightedCursor = flowers_db.rawQuery(sightedIndex, null);
        sightedCursor.moveToFirst();
        sightedCursor.close();


    }


}
