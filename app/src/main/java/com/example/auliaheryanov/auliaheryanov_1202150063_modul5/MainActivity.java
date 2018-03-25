package com.example.auliaheryanov.auliaheryanov_1202150063_modul5;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SharedPreferences pref;
    private SharedPreferences.Editor prefEdit;
    private int cardColor;
    private int optionCardColor;
    private ToDoDB toDoDB;
    private ArrayList<Model> todos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), ToDoAdd.class), 201);
            }
        });


        //inisiasi shared preferenced
        pref = getApplicationContext().getSharedPreferences("pref",0);
        //inisiasi shared preferenced agar bisa diedit
        prefEdit=pref.edit();


        //Inisialisasi RecyclerView
        recyclerView= findViewById(R.id.recyclerviewTodos);


        //menginisiasi database untuk list to do
        toDoDB = new ToDoDB(this);
        //load isi data to do
        loadTodoData();
        //method untuk mengatur tingkah laku recycleview
        settingRecyclerBehavior();
    }

    //merefresh list item
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==201){
            if(resultCode==RESULT_OK){
                loadTodoData();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflater untuk toolbar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //mengambil input
        int id = item.getItemId();
        //mengubah background jika dipilih menu tertentu
        if (id == R.id.action_settings) {
            changeBgItemColor();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //mengambil data dari database
    public void loadTodoData(){
        //pengecekan isi database
        SQLiteDatabase db = toDoDB.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM todolist",null);

        res.moveToFirst();

        //memasukkan data ke recycleview
        todos = new ArrayList<>();
        while(res.isAfterLast() == false){
            int id = res.getInt(res.getColumnIndex("id"));
            String nama = res.getString(res.getColumnIndex("name"));
            String desc = res.getString(res.getColumnIndex("desc"));
            int prior = res.getInt(res.getColumnIndex("prior"));

            Model todo = new Model(nama,desc,prior);
            todo.setId(id);
            todos.add(todo);

            res.moveToNext();
        }


        Adapter todoAdapter = new Adapter(todos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(todoAdapter);

        res.close();
    }

    //yang ngatur swipe2
    public void settingRecyclerBehavior(){
        // apa yg terjadi klo di swipe
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                deleteIt(position);
            }
        };

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    //ngapus item
    public void deleteIt(int pos){
        //ngambil posisi item
        final Model todo = todos.get(pos);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Hapus Item");
        alertDialog.setMessage("Hapus Item '"+todo.getName()+"' ?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //diapus di database sesuai id
                if(toDoDB.ToDoDBDelete(todo.getId())){
                    loadTodoData();
                    Toast.makeText(getApplicationContext(), "Berhasil Menghapus "+todo.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                loadTodoData();
            }
        });
        alertDialog.show();
    }

    //self explanatory
    public void changeBgItemColor(){
        //inisiasi warna default
        cardColor = R.color.shapeDefault;
        //mengambil id radiobutton untuk warna
        optionCardColor = pref.getInt("optionShapeColorSelected",R.id.rShapeColorDefault);
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Shape Color"); //Mengatur Judul
        //mengubah layout xml menjadi kodingan
        final View dialogView = getLayoutInflater().inflate(R.layout.color,null);
        //mengambil radiogroup dari xml
        final RadioGroup rg =(RadioGroup)dialogView.findViewById(R.id.rgShapeColor);
        //cek warna default
        rg.check(optionCardColor);
        //
        dialog.setView(dialogView);
        //perintah saat tombol done pada warna ditekan
        dialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //cek id warna yang dipilih
                switch(rg.getCheckedRadioButtonId()){
                    case R.id.rShapeColorDefault: optionCardColor=R.id.rShapeColorDefault; cardColor=R.color.shapeDefault; break;
                    case R.id.rShapeColorRed: optionCardColor=R.id.rShapeColorRed; cardColor=R.color.shapeRed; break;
                    case R.id.rShapeColorBlue: optionCardColor=R.id.rShapeColorBlue; cardColor=R.color.shapeBlue; break;
                    case R.id.rShapeColorGreen: optionCardColor=R.id.rShapeColorGreen; cardColor=R.color.shapeGreen; break;
                }
                //set warna
                setTodoItemBg();
            }
        });
        //saat ditekan tombol cancel
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    //method setelah pemilihan warna
    public void setTodoItemBg(){
        String color = getResources().getString(cardColor);
        prefEdit.putInt("optionShapeColorSelected",optionCardColor);
       //simpan warna
        prefEdit.putInt("shapeColor",cardColor);
        prefEdit.putString("shapeColorTXT",color);
        prefEdit.commit();

        loadTodoData();
    }


}
