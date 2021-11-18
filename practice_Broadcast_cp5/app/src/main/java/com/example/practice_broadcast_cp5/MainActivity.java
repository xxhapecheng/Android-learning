package com.example.practice_broadcast_cp5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends BaseActivity {

//    private IntentFilter intentFilter;
//    private LocalRecevier localRecevier;
//    private LocalBroadcastManager localBroadcastManager;
//    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        intentFilter = new IntentFilter();
//        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        networkChangeReceiver = new NetworkChangeReceiver();
//        registerReceiver(networkChangeReceiver, intentFilter);
        Button button = (Button) findViewById(R.id.force_offline);
//        localBroadcastManager = LocalBroadcastManager.getInstance(this);//获取实例

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("Force_offline.test");
//                localBroadcastManager.sendBroadcast(intent);
                sendBroadcast(intent);
            }
        });
//        intentFilter = new IntentFilter();
//        intentFilter.addAction("fuck broadcast");
//        localRecevier = new LocalRecevier();
//        localBroadcastManager.registerReceiver(localRecevier, intentFilter);
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        localBroadcastManager.unregisterReceiver(localRecevier);
//    }
//
//    class LocalRecevier extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Toast.makeText(context, "receive broadcast", Toast.LENGTH_LONG).show();
//        }
//    }

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