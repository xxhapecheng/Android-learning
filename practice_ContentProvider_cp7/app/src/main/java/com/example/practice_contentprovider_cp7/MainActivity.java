package com.example.practice_contentprovider_cp7;

import android.Manifest;
import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Button makeCall=(Button)findViewById(R.id.make_call);


        //查询联系人
        ListView contactView=(ListView) findViewById(R.id.contacts_view);
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,contactsList);
        contactView.setAdapter(adapter);
        if(ContextCompat.checkSelfPermission(MainActivity.this, permission.READ_CONTACTS)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{permission.READ_CONTACTS},1);
        }else{
            readContacts();
        }
        Toast.makeText(this,"finish",Toast.LENGTH_LONG);

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