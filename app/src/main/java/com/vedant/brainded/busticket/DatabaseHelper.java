package com.vedant.brainded.busticket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dell on 6/12/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "users";
    private static final String COL1 = "name";
    private static final String COL2 = "phone";
    private static final String COL3 = "email";
    private static final String COL4 = "seat";

    public DatabaseHelper(Context context){
        super(context, TABLE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        String createTable = "CREATE TABLE " + TABLE_NAME + "("+COL1+" VARCHAR(50), " + COL2 + " VARCHAR(10), " + COL3 + " VARCHAR(50), " + COL4 + " INT)";
        sqLiteDatabase.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addData(String name, String phone, String email, int seat){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, name);
        contentValues.put(COL2, phone);
        contentValues.put(COL3, email);
        contentValues.put(COL4, seat);

        db.insert(TABLE_NAME, null, contentValues);

    }

    public boolean CheckIsDataAlreadyInDBorNot(String TableName,
                                               String dbfield, String fieldValue) {
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "Select * from " + TableName + " where " + dbfield + " = " + fieldValue;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public void deleteAll(String TableName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TableName);
    }


}
