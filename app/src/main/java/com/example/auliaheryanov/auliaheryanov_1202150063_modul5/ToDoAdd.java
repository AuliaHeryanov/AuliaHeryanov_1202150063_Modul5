package com.example.auliaheryanov.auliaheryanov_1202150063_modul5;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ToDoAdd extends AppCompatActivity {

    private EditText mName;
    private EditText mDesc;
    private EditText mPriority;
    private Button mAdd;

    private ToDoDB toDoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_add);
        setTitle("Add To Do");

        //inisiasi ToDoDB
        toDoDB = new ToDoDB(this);

        mAdd= findViewById(R.id.btnTodoAdd);
        mName= findViewById(R.id.toDoName);
        mDesc= findViewById(R.id.toDoDesc);
        mPriority= findViewById(R.id.toDoPriority);

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTodoData();
            }
        });
    }


    //menambahkan data ke DB
    public void addTodoData(){
        String nama = mName .getText().toString();
        String desc = mDesc.getText().toString();
        int prior = Integer.parseInt(mPriority.getText().toString());

        //query nambah data
        String sqlAdd = "INSERT INTO todolist(name, desc, prior) VALUES(?,?,?)";
        SQLiteDatabase db = toDoDB.getWritableDatabase();
        //compile statement yang masih berbentuk abstrak
        SQLiteStatement stmt = db.compileStatement(sqlAdd);
        //complie nama
        stmt.bindString(1,nama);
        //compile desc
        stmt.bindString(2,desc);
        //compile priority
        stmt.bindLong(3,prior);

        //eksekusi statement
        long rowId = stmt.executeInsert();

        //Pengecekan Ekseskusi, jika berhasil maka mengembalikan id insert, jika tidak -1
        if(rowId!=-1){
            Toast.makeText(this, "Tambah ToDo Berhasil ("+rowId+")", Toast.LENGTH_SHORT).show();
            Intent ini = getIntent();
            //Memberikan nilai tambahan ke intent yang sudah menunggu (MainActivity)
            ini.putExtra("EXTRA_INSERT_RESULT",rowId);
            //Memberikan nilai hasil saat Activity berakhir
            setResult(Activity.RESULT_OK,ini);
            finish();
        }
    }
}
