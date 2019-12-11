package com.example.to_do_list.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.to_do_list.models.Lista;

import java.util.List;

@Dao
public interface ListaDao {

    @Insert
    void insertListas(Lista... listas);

    @Query("SELECT * FROM listas")
    LiveData<List<Lista>> getListas();

    @Query("DELETE FROM tarefas WHERE listaid = :listaId")
    void deleteTarefasFromLista(int listaId);

    @Delete
    int delete(Lista... listas);

    @Update
    int update(Lista... listas);
}
