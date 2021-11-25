package com.example.practice_learningmore_cp13;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContextTest.showContextUse();
        Intent intent=new Intent(MainActivity.this,MainActivity3.class);
        person p=new person();
        p.setName("xxhape");
        p.setAge(21);
        intent.putExtra("personData",p);
        startActivity(intent);
    }
}