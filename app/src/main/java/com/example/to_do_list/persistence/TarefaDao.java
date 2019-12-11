package com.example.to_do_list.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.to_do_list.models.Tarefa;

import java.util.List;

@Dao
public interface TarefaDao {

    @Insert
    void insertTarefas(Tarefa... tarefas);

    @Query("SELECT * FROM tarefas WHERE listaid = :listaId")
    LiveData<List<Tarefa>> getTarefas(int listaId);

    @Delete
    int delete(Tarefa... tarefas);

    @Update
    int update(Tarefa... tarefas);
}
