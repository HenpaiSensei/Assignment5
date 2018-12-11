package com.cis4301.henry.assignment5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.sqlite.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase flowers_db = SQLiteDatabase.openDatabase("data/data/com.cis4301.henry.assignment5/databases/flowers.db",  null, 0 );


    }
}
