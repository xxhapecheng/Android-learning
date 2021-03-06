package com.example.practice_activity_cp2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

    public static final String TAG="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, firstActivity.class);
                startActivityForResult(intent,1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "hello baidu";
                Intent intent= new Intent(MainActivity.this, secondActivity.class);
                intent.putExtra("extra_data",data);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onstart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onresume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onpause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onstop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"ondestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onrestart");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                Toast.makeText(this,"you click add",Toast.LENGTH_LONG).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this,"you click remove",Toast.LENGTH_LONG).show();
                break;
            case R.id.quit_item:
                ActivityCollector.finnishALL();
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if(resultCode == RESULT_OK){
                    String returnedData = data.getStringExtra("data_return");
                    Log.d(TAG,returnedData);
                }
                break;
            default:
        }
    }
}