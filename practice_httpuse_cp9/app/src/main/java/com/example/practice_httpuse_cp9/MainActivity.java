package com.example.practice_httpuse_cp9;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        HttpUtil.sendOkHttpRequest("http://www.baidu.com",new okhttp3.Callback(){
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                String responseData=response.body().string();
//                Log.d("MainActivity",responseData);
//            }
//
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                e.printStackTrace();
//            }
//        });
        TextView textView=(TextView)findViewById(R.id.gogoog);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtil.sengHttpRequest("http://www.baidu.com", new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        Log.d("MainActivity",response);
                    }

                    @Override
                    public void onErroi(Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });

    }
}