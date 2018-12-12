package com.cis4301.henry.assignment5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.sqlite.*;
import android.database.Cursor;
import android.text.Editable;
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

public class InsertSightings extends AppCompatActivity {
    ArrayList<String> commonNameList = new ArrayList<String>();
    EditText name;
    EditText person;
    EditText location;
    EditText date;
    SQLiteDatabase flowers_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_sightings);
        name= findViewById(R.id.nameEdit);
        person=findViewById(R.id.personEdit);
        location=findViewById(R.id.loationEdit);
        date=findViewById(R.id.dateEdit);
        flowers_db = SQLiteDatabase.openDatabase("data/data/com.cis4301.henry.assignment5/databases/flowers.db", null, 0);

    }

    public boolean flowerExists(Editable comname) {
        String getComNames = "SELECT COMNAME FROM FLOWERS WHERE FLOWERS.COMNAME="+"'"+comname+"'";

        final Cursor cursor = flowers_db.rawQuery(getComNames, null);

        if(cursor != null && cursor.moveToFirst())
            do{
                return true;
            } while(cursor.moveToNext());
        return false;
    }
    public void insertSighting(View view){

        if(name.getText()!=null && flowerExists(name.getText())==true) {
            if (location.getText()!=null&& person.getText()!=null&&date.getText()!=null) {
                String insertQuery = "INSERT INTO SIGHTINGS (NAME, PERSON, LOCATION, SIGHTED)" +
                        "VALUES ('" + name.getText() + "', '" + person.getText() + "','" + location.getText() + "','" + date.getText() + "')";
                Cursor cursor = flowers_db.rawQuery(insertQuery, null);
                cursor.moveToFirst();
                cursor.close();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"You have to insert a flower in our database or you have to fill all the Text Boxes",Toast.LENGTH_LONG);

            return;
        }
    }
    public void returnOnClick(View view){
        finish();
    }

}
