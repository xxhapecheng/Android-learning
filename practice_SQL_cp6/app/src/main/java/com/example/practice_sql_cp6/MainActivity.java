package com.example.practice_sql_cp6;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private MydatabaseHelper dbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbhelper=new MydatabaseHelper(this,"BookStore.db",null,1);
        Button createdatabase=(Button) findViewById(R.id.create_database);
        createdatabase.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dbhelper.getWritableDatabase();
            }
        });
    }
}