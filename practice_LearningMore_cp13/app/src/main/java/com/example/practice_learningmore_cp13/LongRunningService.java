package com.example.practice_learningmore_cp13;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

public class LongRunningService extends Service {

    public LongRunningService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                
            }
        }).start();
        AlarmManager manager=(AlarmManager) getSystemService(ALARM_SERVICE);
        int anhour=60*60*1000;
        long triggerAttime= SystemClock.elapsedRealtime()+anhour;
        Intent i =new Intent(this,LongRunningService.class);
        PendingIntent po=PendingIntent.getService(this,0,i,0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAttime,po);
        return super.onStartCommand(intent,flags,startId);
    }
}