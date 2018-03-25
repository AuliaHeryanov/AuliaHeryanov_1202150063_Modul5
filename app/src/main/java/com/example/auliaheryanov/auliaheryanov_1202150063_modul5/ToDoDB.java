package com.example.auliaheryanov.auliaheryanov_1202150063_modul5;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Aulia Heryanov on 25/03/2018.
 */

public class ToDoDB extends SQLiteOpenHelper {
    private static final String DB_NAME="todo.db";
    private static final int DB_VERSION=1;

    public ToDoDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //membuat tabel
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE todolist(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NULL, desc TEXT NULL, prior INTEGER NULL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //menghapus database
    public boolean ToDoDBDelete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        int delete = db.delete("todolist","id = "+id, null);
        return delete>0;
    }
}
