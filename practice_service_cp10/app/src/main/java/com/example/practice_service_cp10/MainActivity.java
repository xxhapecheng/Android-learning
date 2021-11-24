package com.example.practice_service_cp10;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public static final String CHANNEL_ID="channel01";
    private TextView textView;
    public static  final int update_text=1;
    private MyService.DownloadBinder downloadBinder;

    private ServiceConnection connection =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder=(MyService.DownloadBinder) service;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void createNotificationChannel() {
        if(VERSION.SDK_INT>= 26){
            NotificationChannel serviceChannel=new NotificationChannel(CHANNEL_ID,"channel01", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(serviceChannel);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();
        setContentView(R.layout.activity_main);
        Button startService=(Button) findViewById(R.id.start_service);
        Button stopService=(Button) findViewById(R.id.stop_service);
        Button bindService=(Button)findViewById(R.id.bind_service);
        Button unbindService=(Button)findViewById(R.id.unbind_service);
        Button startIntentService=(Button)findViewById(R.id.start_intent_service);
        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent=new Intent(MainActivity.this,MyService.class);
                startService(startIntent);
            }
        });

        stopService.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stopIntent=new Intent(MainActivity.this,MyService.class);
                stopService(stopIntent);
            }
        });

        bindService.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bindIntent = new Intent(MainActivity.this,MyService.class);
                bindService(bindIntent,connection,BIND_AUTO_CREATE);
            }
        });

        unbindService.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(connection);
            }
        });

        startIntentService.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity","Thread id is"+Thread.currentThread().getId());
                Intent intentService=new Intent(MainActivity.this,MyintentService.class);
                startService(intentService);
            }
        });

    }


}