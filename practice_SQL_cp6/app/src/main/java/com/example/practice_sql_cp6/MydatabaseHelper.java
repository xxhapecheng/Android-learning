package com.example.practice_sql_cp6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;

public class MydatabaseHelper extends SQLiteOpenHelper {

    public static final String create_book="create table book ("+"id integer primary key autoincrement,"
            +"author text,"
            +"prive real,"
            +"pages integer,"
            +"name text)";
    private Context mcontext;
    public MydatabaseHelper(Context context,  String name, SQLiteDatabase.CursorFactory factory,
            int version) {
        super(context, name, factory, version);
        mcontext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_book);
        Toast.makeText(mcontext,"create succeeded",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
