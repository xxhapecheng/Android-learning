package com.example.practice_broadcast_cp5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

//    private IntentFilter intentFilter;
//    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        intentFilter = new IntentFilter();
//        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        networkChangeReceiver = new NetworkChangeReceiver();
//        registerReceiver(networkChangeReceiver, intentFilter);
        Button button = (Button) findViewById(R.id.buttoncast);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("MY_Broadcast");
                sendBroadcast(intent);
            }
        });
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unregisterReceiver(networkChangeReceiver);//动态注册的广播接收器要取消才行。
//    }
//
//    class NetworkChangeReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(
//                    Context.CONNECTIVITY_SERVICE);
//            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();//获取网络信息 network info实例
//            if (networkInfo != null && networkInfo.isAvailable()) { //判断网络是否可用
//                Toast.makeText(context, "network is available", Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(context, "network is unavailable", Toast.LENGTH_LONG).show();
//            }
//
//        }
//    }
}