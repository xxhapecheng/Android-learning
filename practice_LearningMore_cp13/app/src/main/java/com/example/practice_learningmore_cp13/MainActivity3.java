package com.example.practice_learningmore_cp13;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        person p2=(person) getIntent().getSerializableExtra("personData");
        Log.d("MainActivity2",p2.getName()+"  "+p2.getAge() );
    }
}