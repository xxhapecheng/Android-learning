package com.example.practice_ui_cp3;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ListviewActivity extends AppCompatActivity {

    private String[] data = {"Apple","banana","orange","watermelon","pear","cherry"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListviewActivity.this, android.R.layout.simple_list_item_1,data);
        ListView listView  = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }
}
