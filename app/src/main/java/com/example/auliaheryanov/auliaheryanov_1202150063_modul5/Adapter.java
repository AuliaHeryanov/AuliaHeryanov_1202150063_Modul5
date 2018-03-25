package com.example.auliaheryanov.auliaheryanov_1202150063_modul5;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Aulia Heryanov on 25/03/2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.TodoViewholder>{

    private ArrayList<Model> list;

    public Adapter(ArrayList<Model> list) {
        this.list = list;
    }

    //inflate dari todo_item
    @Override
    public TodoViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.todo_item, parent, false);
        return new TodoViewholder(view);
    }

    //memasukkan data ke posisi masing2
    @Override
    public void onBindViewHolder(TodoViewholder holder, int position) {
        Model item = list.get(position);
        holder.bindTo(item);
    }

    @Override
    public int getItemCount() {
        return list.size()>0?list.size():0;
    }

    class TodoViewholder extends RecyclerView.ViewHolder{

        private TextView lblTodoName, lblTodoDesc, lblTodoPrior;
        private Model currTodo;
        private CardView mCardView;

        public TodoViewholder(View itemView) {
            super(itemView);
            lblTodoName= itemView.findViewById(R.id.lblTodoNama);
            lblTodoDesc= itemView.findViewById(R.id.lblTodoDesc);
            lblTodoPrior= itemView.findViewById(R.id.lblTodoPrior);
            mCardView= itemView.findViewById(R.id.todoCardView);

            SharedPreferences pref = itemView.getContext().getSharedPreferences("pref",0);
            String colored = pref.getString("shapeColorTXT","#FFFFFF");
            mCardView.setCardBackgroundColor(Color.parseColor(colored));
        }

        public void bindTo(Model todoModel){
            currTodo = todoModel;
            lblTodoName.setText(currTodo.getName());
            lblTodoDesc.setText(currTodo.getDescription());
            lblTodoPrior.setText(""+currTodo.getPriority());
        }
    }
}

