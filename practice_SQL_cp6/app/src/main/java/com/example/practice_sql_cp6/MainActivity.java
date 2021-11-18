package com.example.practice_sql_cp6;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

public class MainActivity extends AppCompatActivity {

    private MydatabaseHelper dbhelper;
    private EditText editText;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //String inputtext=editText.getText().toString();
        //save(inputtext);
    }

//    private void save(String inputtext) {
//        FileOutputStream out=null;
//        BufferedWriter writer=null;
//        try {
//            out=openFileOutput("data", Context.MODE_PRIVATE);
//            writer=new BufferedWriter(new OutputStreamWriter(out));
//            writer.write(inputtext);
//        }catch (IOException e){
//            e.printStackTrace();
//        }finally {
//            try {
//                if(writer!=null){
//                    writer.close();
//                }
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }
//
//
//    }
//    public String load(){
//        FileInputStream in= null;
//        BufferedReader reader = null;
//        StringBuilder content= new StringBuilder();
//        try {
//            in=openFileInput("data");
//            reader =new BufferedReader(new InputStreamReader(in));
//            String line="";
//            while((line=reader.readLine())!=null){
//                content.append(line);
//            }
//        }catch (IOException e){
//            e.printStackTrace();
//        }finally {
//            if(reader!=null){
//                try {
//                    reader.close();
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//            }
//        }
//        return content.toString();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        editText=(EditText) findViewById(R.id.edit);
//        String inputt=load();
//        if(!TextUtils.isEmpty(inputt)){
//            editText.setText(inputt);
//            editText.setSelection(inputt.length());
//            Toast.makeText(this,"restoring succeeded",Toast.LENGTH_LONG).show();
//        }
        //dbhelper=new MydatabaseHelper(this,"BookStore.db",null,);
        Button createdatabase=(Button) findViewById(R.id.create_database);
        createdatabase.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Connector.getDatabase();
            }
        });
        Button deleteData=(Button)findViewById(R.id.delete_data);
        Button updateData=(Button) findViewById(R.id.update_data);
        Button queryData=(Button) findViewById(R.id.select_data);
        Button adddata=(Button) findViewById(R.id.add_data);

        adddata.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book=new Book();
                book.setName("good play");
                book.setAuthor("dan cheng");
                book.setPages(456);
                book.setPrice(17.32);
                book.save();
            }
        });

        updateData.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book=new Book();
                book.setPrice(9.99);
                book.updateAll("name=? and author=?","good play","dan cheng");
            }
        });
        queryData.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Book> books= DataSupport.findAll(Book.class);
                for(Book book:books){
                    Log.d("MainActivity","book name is "+book.getName());
                    Log.d("MainActivity","book author is "+book.getAuthor());
                    Log.d("MainActivity","book pages is "+book.getPages());
                    Log.d("MainActivity","book price is "+book.getPrice());
                }
            }
        });
//        adddata.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SQLiteDatabase db =dbhelper.getWritableDatabase();
//                ContentValues values=new ContentValues();
//                values.put("name","the da vinci code");
//                values.put("author","dan brown");
//                values.put("pages",545);
//                values.put("price",16.96);
//                db.insert("book",null,values);
//                values.clear();
//                values.put("name","the lost symblo");
//                values.put("author","dan brown");
//                values.put("pages",510);
//                values.put("price",19.95);
//                db.insert("book",null,values);
//            }
//        });
//
//        updateData.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SQLiteDatabase db=dbhelper.getWritableDatabase();
//                ContentValues values =new ContentValues();
//                values.put("price",10.99);
//                db.update("book",values,"name=?",new String[]{"the da vinci code"});
//            }
//        });
//
//        deleteData.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SQLiteDatabase db=dbhelper.getWritableDatabase();
//                db.delete("book","pages>?",new String[]{"500"});
//            }
//        });
//
//        queryData.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SQLiteDatabase db =dbhelper.getWritableDatabase();
//                Cursor cursor=db.query("book",null,null,null,null,null,null);
//                if(cursor.moveToFirst()){
//                    do{
//                        @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
//                        @SuppressLint("Range") String author=cursor.getString(cursor.getColumnIndex("author"));
//                        @SuppressLint("Range") int pages=cursor.getInt(cursor.getColumnIndex("pages"));
//                        @SuppressLint("Range") double price=cursor.getDouble(cursor.getColumnIndex("price"));
//                        Log.d("MainActivity","book name is "+name);
//                        Log.d("MainActivity","book author is "+author);
//                        Log.d("MainActivity","book pages is "+pages);
//                        Log.d("MainActivity","book price is "+price);
//
//                    }while(cursor.moveToNext());
//                }
//                cursor.close();
//            }
//        });

    }
}