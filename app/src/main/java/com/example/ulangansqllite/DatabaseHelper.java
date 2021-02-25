package com.example.ulangansqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "ulangansqllite.db";
    private static final int DB_VERSION = 2;
    private static String TABLE_NAME = "content";
    static final String ID_ = "id_";
    static final String JUDUL = "judul";
    static final String DESK = "desckripsi";
    static final String TGL = "tgl";

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createContentTable = "CREATE TABLE " + TABLE_NAME + "("+ ID_ + " INTEGER PRIMARY KEY," + JUDUL + " TEXT NOT NULL," + DESK + " TEXT NOT NULL," + TGL + " TEXT NOT NULL"+")";
        db.execSQL(createContentTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int OldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insert(Content content){
        SQLiteDatabase db = getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date tgl = new Date();
        ContentValues values = new ContentValues();
        values.put(JUDUL, content.getJudul());
        values.put(DESK, content.getDesk());
        values.put(TGL, dateFormat.format(tgl));
        db.insert(TABLE_NAME, null, values);
    }

    public List<Content> selectContentList(){
        ArrayList<Content> contentList = new ArrayList<Content>();

        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {ID_, JUDUL, DESK, TGL};

        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String judul = cursor.getString(1);
            String desk = cursor.getString(2);
            String tgl =  cursor.getString(3);

            Content content = new Content();
            content.setId(id);
            content.setJudul(judul);
            content.setDesk(desk);
            content.setTgl(tgl);

            contentList.add(content);
        }
        return contentList;
    }

    public void btnUpdate (Content content){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date tgl = new Date();

        values.put(JUDUL, content.getJudul());
        values.put(DESK, content.getDesk());
        values.put(TGL, dateFormat.format(tgl));
        String whereClause = ID_ + " = ' " + content.getId() + " ' ";
        db.update(TABLE_NAME, values, whereClause, null);
    }

    public void delete (int ID){
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = ID_ + "= '" + ID + "'";

        db.delete(TABLE_NAME, whereClause, null);
    }

}
