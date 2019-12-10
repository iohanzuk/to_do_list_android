package com.example.to_do_list.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.to_do_list.models.Lista;
import com.example.to_do_list.models.Tarefa;

@Database(entities ={Tarefa.class, Lista.class}, version = 1)
public abstract class ToDoListDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "to_do_list";

    private static ToDoListDatabase instance;

    static ToDoListDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    ToDoListDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract TarefaDao getTarefaDao();
    public abstract ListaDao getListaDao();
}
