package com.example.practice_contentprovider_cp7;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class DataBaseProvider extends ContentProvider {

    public static final int BOOK_DIR=0;
    public static final int BOOK_ITEM=1;
    public static final int CATEGORY_DIR=2;
    public static final int CATEGORY_ITEM=3;
    public static final String AUTHORITY="com.example.practice_contentprovider_cp7.provider";
    private static UriMatcher uriMatcher;
    private MydatabaseHelper dbHelper;

    static {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"book",BOOK_DIR);
        uriMatcher.addURI(AUTHORITY,"book/#",BOOK_ITEM);
        uriMatcher.addURI(AUTHORITY,"category",CATEGORY_DIR);
        uriMatcher.addURI(AUTHORITY,"category/#",CATEGORY_ITEM);
    }

    public DataBaseProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        int deletedrows =0;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                deletedrows= db.delete("book",selection, selectionArgs);
                break;
            case BOOK_ITEM:
                String bookid = uri.getPathSegments().get(1);
                deletedrows = db.delete("book", "id=?", new String[]{bookid});
                break;
            case CATEGORY_DIR:
                deletedrows = db.delete("category", selection, selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryid = uri.getPathSegments().get(1);
                deletedrows= db.delete("category", "id=?", new String[]{categoryid});
                break;
            default:
                break;
        }


        return deletedrows;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                return "vnd.android.cursor.dir/vnd.com.android.example.provider.book";
            case BOOK_ITEM:
                return "vnd.android.cursor.item/vnd.com.android.example.provider.book";
            case CATEGORY_DIR:
                return "vnd.android.cursor.dir/vnd.com.android.example.provider.category";
            case CATEGORY_ITEM:
                return "vnd.android.cursor.item/vnd.com.android.example.provider.category";
            default:
                break;
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Uri urireturn=null;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
            case BOOK_ITEM:
                long  newbookid=db.insert("book",null,values);
                urireturn=Uri.parse("content://"+AUTHORITY+"/book/"+newbookid);
                break;
            case CATEGORY_DIR:
            case CATEGORY_ITEM:
                long newCategoryID=db.insert("category",null,values);
                urireturn=Uri.parse("content://"+AUTHORITY+"/category/"+newCategoryID);
                break;
            default:
                break;
        }
        return urireturn;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        dbHelper=new MydatabaseHelper(getContext(),"BookStore.db",null,2);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor=null;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                cursor=db.query("book",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case BOOK_ITEM:
                String bookID=uri.getPathSegments().get(1);
                cursor=db.query("book",projection,"id=?",new String[]{bookID},null,null,sortOrder);
                break;
            case CATEGORY_DIR:
                cursor=db.query("category",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case CATEGORY_ITEM:
                String categoryID=uri.getPathSegments().get(1);
                cursor=db.query("category",projection,"id=?",new String[]{categoryID},null,null,sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        int updatedRows=0;
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                updatedRows = db.update("book", values, selection, selectionArgs);
                break;
            case BOOK_ITEM:
                String bookid = uri.getPathSegments().get(1);
                updatedRows = db.update("book", values, "id=?", new String[]{bookid});
                break;
            case CATEGORY_DIR:
                updatedRows = db.update("category", values, selection, selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryid = uri.getPathSegments().get(1);
                updatedRows = db.update("category", values, "id=?", new String[]{categoryid});
                break;
            default:
                break;
        }
        return updatedRows;
    }
}