package com.example.practice_phonemedia_cp8;

import android.Manifest;
import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final int TAKE_PHOTO=1;
    public static final int CHOOSE_PHOTO=2;

    private ImageView picture;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ameralbumtestt);
        Button button =(Button)findViewById(R.id.send_notice);
        Button takephoto=(Button)findViewById(R.id.take_photo);
        Button choosePhoto=(Button)findViewById(R.id.choose_from_album);
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

        choosePhoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(MainActivity.this, permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{permission.WRITE_EXTERNAL_STORAGE},1);
                }else{
                    openAlbum();
                }
            }
        });
    }

    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else{
                    Toast.makeText(this, "you denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
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

            case CHOOSE_PHOTO:
                Log.d("MainActivity","xuanze");
                if(resultCode == RESULT_OK){
                    if(VERSION.SDK_INT>=19){
                        handleImageOnKitKat(data);
                    }else{
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:Log.d("MainActicity","默认");
                break;
        }
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri =data.getData();
        String imagePath=getImagePath(uri,null);
        disPlayImage(imagePath);
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        Log.d("MainActivity","chuli");
        String imagePath=null;
        Uri uri=data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){
            //如果是document类型的uri，通过这个id处理
            String docID=DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docID.split(":")[1];//解析为数字格式id
                String selection= Media._ID+"="+id;
                imagePath=getImagePath(Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docID));
                imagePath=getImagePath(contentUri,null);
            }else if("content".equalsIgnoreCase(uri.getScheme())){
                //普通类型普通方法
                imagePath=getImagePath(uri,null);
            }else if("file".equalsIgnoreCase(uri.getScheme())){
                //file类型直接用这个获取图片路径
                imagePath=uri.getPath();
            }
            Log.d("MainActivity",imagePath);
            disPlayImage(imagePath);
        }
    }

    @SuppressLint("Range")
    private String getImagePath(Uri uri, String selection) {
        String path=null;
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    private void disPlayImage(String imagePath){
        if(imagePath!=null){
            Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
            Log.d("MainActivity","设置图片"+imagePath);
            picture.setImageBitmap(bitmap);
        }else{
            Toast.makeText(this,"failed to get image",Toast.LENGTH_LONG).show();
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