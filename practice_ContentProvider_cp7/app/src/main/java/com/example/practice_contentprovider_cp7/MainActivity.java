package com.example.practice_contentprovider_cp7;

import android.Manifest;
import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.Tag;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    List<String> contactsList = new ArrayList<>();
    private String newId;
    private static final String TAG="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addData=(Button) findViewById(R.id.add_data);
        Button deleteData=(Button) findViewById(R.id.delete_data);
        Button queryData=(Button) findViewById(R.id.query_data);
        Button updateData=(Button) findViewById(R.id.update_data);

        addData.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse("content://com.example.practice_contentprovider_cp7.provider/book");
                ContentValues values=new ContentValues();
                values.put("name","A clash of kings");
                values.put("author","xxhape");
                values.put("pages",1023);
                values.put("price",22.84);
                Uri newUri=getContentResolver().insert(uri,values);
                newId=newUri.getPathSegments().get(1);
                Log.d(TAG,"add success");
            }
        });

        queryData.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse("content://com.example.practice_contentprovider_cp7.provider/book");
                Cursor cursor=getContentResolver().query(uri,null,null,null,null);
                if(cursor!=null){
                    while(cursor.moveToNext()){
                        @SuppressLint("Range") String name =cursor.getString(cursor.getColumnIndex("name"));
                        @SuppressLint("Range") String author=cursor.getString(cursor.getColumnIndex("author"));
                        @SuppressLint("Range") int pages=cursor.getInt(cursor.getColumnIndex("pages"));
                        @SuppressLint("Range") double price=cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d(TAG,"book name is "+name);
                        Log.d(TAG,"book author is "+author);
                        Log.d(TAG,"book pages is "+pages);
                        Log.d(TAG,"book price is "+price);
                    }
                    cursor.close();
                }
            }
        });

        updateData.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse("content://com.example.practice_contentprovider_cp7.provider/book/"+newId);
                ContentValues values=new ContentValues();
                values.put("name","a storm of swords");
                values.put("pages","1215");
                values.put("price",99.99);
                getContentResolver().update(uri,values,null,null);
                Log.d(TAG,"update success");
            }
        });

        deleteData.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri =Uri.parse("content://com.example.practice_contentprovider_cp7.provider/book/"+newId);
                getContentResolver().delete(uri,null,null);
                Log.d(TAG,"delete success");
            }
        });

        //Button makeCall=(Button)findViewById(R.id.make_call);


        //查询联系人
//        ListView contactView=(ListView) findViewById(R.id.contacts_view);
//        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,contactsList);
//        contactView.setAdapter(adapter);
//        if(ContextCompat.checkSelfPermission(MainActivity.this, permission.READ_CONTACTS)!=PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(MainActivity.this,new String[]{permission.READ_CONTACTS},1);
//        }else{
//            readContacts();
//        }
//        Toast.makeText(this,"finish",Toast.LENGTH_LONG);

//        makeCall.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(ContextCompat.checkSelfPermission(MainActivity.this, permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{permission.CALL_PHONE},1);
//                }
//                else{
//                    call();
//                }
//
//
//
//            }
//        });



    }

    private void readContacts() {
        Cursor cursor=null;
        try {
            cursor=getContentResolver().query(Phone.CONTENT_URI,null,null,null,null);
            if(cursor!=null){
                while (cursor.moveToNext()){
                    String displayname=cursor.getString((cursor.getColumnIndex(Phone.DISPLAY_NAME)));
                    @SuppressLint("Range") String number=cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
                    contactsList.add(displayname+"\n"+number);
                    Log.d("MainActivity",displayname+"\n"+number);
                };
                adapter.notifyDataSetChanged();
                Log.d("MainActivity","done");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }
    }

    private void call(){
        try {
            Intent intent=new Intent();
            intent.setData(Uri.parse("tel:10086"));
            startActivity(intent);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call();
                } else {
                    Toast.makeText(this, "you denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}