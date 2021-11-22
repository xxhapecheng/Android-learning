package com.example.practice_phonemedia_cp8;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final int TAKE_PHOTO=1;
    private ImageView picture;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ameralbumtestt);
        Button button =(Button)findViewById(R.id.send_notice);
        Button takephoto=(Button)findViewById(R.id.take_photo);
        picture=(ImageView)findViewById(R.id.picture);
        takephoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputimage=new File(getExternalCacheDir(),"output_image.jpg");
                try {
                    if(outputimage.exists()){
                        outputimage.delete();
                        Log.d("MainActivity","存在图片");
                    }
                    outputimage.createNewFile();
                    Log.d("Mainactivity","创建新文件");
                }catch (IOException e){
                    e.printStackTrace();
                }
                if(VERSION.SDK_INT>=24){
                    Log.d("Mainactivity","get uri");
                    imageUri = FileProvider.getUriForFile(MainActivity.this,"com.example.practice_phonemedia_cp8.fileprovider",outputimage);
                }else{
                    imageUri=Uri.fromFile(outputimage);
                }
                Intent intent =new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("MainActicity","进入");
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode== RESULT_OK){
                    try {
                        Log.d("MainActicity","转换图片");
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                Log.d("MainActicity","未进入");
                break;
            default:Log.d("MainActicity","默认");
                break;
        }
    }

    //    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.send_notice:
//                NotificationManager manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                Notification notification = new NotificationCompat.Builder(this)
//                        .setContentText("this is content title")
//                        .setContentText("This is content text")
//                        .setWhen(System.currentTimeMillis())
//                        .setSmallIcon(R.mipmap.ic_launcher)
////                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
//                        //.setContentIntent(pi)
//                        //        .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg")))
//                        //        .setVibrate(new long[]{0, 1000, 1000, 1000})
//                        //        .setLights(Color.GREEN, 1000, 1000)
//                        .setDefaults(NotificationCompat.DEFAULT_ALL)
//                        //        .setStyle(new NotificationCompat.BigTextStyle().bigText("Learn how to build notifications, send and sync data, and use voice actions. Get the official Android IDE and developer tools to build apps for Android."))
//                        //.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.big_image)))
//                        //.setPriority(NotificationCompat.PRIORITY_MAX)
//                        .build();
//                manager.notify(1, notification);
//                break;
//            default:
//                break;
//        }
//    }
}