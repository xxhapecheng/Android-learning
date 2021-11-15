package com.example.practice_activity_cp2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class firstActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fisrt_layout);
//        Intent intent=getIntent();
//        String data=intent.getStringExtra("extra_data");
//        Log.d("FirstActivity",data);
        Button b2 = (Button) findViewById(R.id.button3);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("data_return","hello mainactivity");
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
