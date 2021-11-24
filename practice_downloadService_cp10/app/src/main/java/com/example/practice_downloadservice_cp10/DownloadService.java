package com.example.practice_downloadservice_cp10;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationCompat.Builder;
import java.io.File;

public class DownloadService extends Service {

    private DowanloadTask dowanloadTask;
    private String downloadUrl;
    private DownloadListener listener=new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1,getNotification("Downloading...",progress));
        }

        @Override
        public void onSuccess() {
            dowanloadTask=null;
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("Download Success",-1));
            Toast.makeText(DownloadService.this,"Download Success",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailed() {
            dowanloadTask=null;
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("download failed",-1));
            Toast.makeText(DownloadService.this,"download failed",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onPaused() {
            dowanloadTask=null;
            Toast.makeText(DownloadService.this,"paused",Toast.LENGTH_LONG).show();

        }

        @Override
        public void onCanceled() {
            dowanloadTask=null;
            stopForeground(true);
            Toast.makeText(DownloadService.this,"canceled",Toast.LENGTH_LONG).show();
        }
    };

    private DownloadBingder mbinder=new DownloadBingder();

    class DownloadBingder extends Binder{
        public void startDownload(String url){
            if(dowanloadTask==null){
                downloadUrl=url;
                dowanloadTask=new DowanloadTask(listener);
                dowanloadTask.execute(downloadUrl);
                startForeground(1,getNotification("Downloading...",0));
                Toast.makeText(DownloadService.this,"Downloading",Toast.LENGTH_LONG).show();
            }
        }

        public void pauseDownload(){
            if(dowanloadTask!=null){
                dowanloadTask.pauseDownload();
            }
        }

        public  void cancelDownload(){
            if(dowanloadTask!=null){
                dowanloadTask.cancelDownload();
            }else {
                if(downloadUrl!=null){
                    String fileName=downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                    String directory= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(directory+fileName);
                    if(file.exists()){
                        file.delete();
                    }
                    getNotificationManager().cancel(1);
                    stopForeground(true);
                    Toast.makeText(DownloadService.this,"canceled",Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    public DownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mbinder;
    }

    private NotificationManager getNotificationManager(){
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }
    private Notification getNotification(String title ,int progress){
        Intent intent =new Intent(this, MainActivity.class);
        PendingIntent pi =PendingIntent.getActivity(this,0,intent ,0);
        NotificationCompat.Builder builder=new Builder(this,"channel1");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        builder.setContentTitle(title);
        builder.setContentIntent(pi);
        if(progress>0){
            builder.setContentText(progress+"%");
            builder.setProgress(100,progress,false);
        }
        return builder.build();
    }
}