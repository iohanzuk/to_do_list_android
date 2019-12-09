package com.example.to_do_list;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.to_do_list.models.Lista;
import com.example.to_do_list.models.Tarefa;

@Database(entities = {Lista.class , Tarefa.class}, version = 1)
public abstract class MyAppDatabase extends RoomDatabase {
    public abstract MyDAO myDAO();
}
