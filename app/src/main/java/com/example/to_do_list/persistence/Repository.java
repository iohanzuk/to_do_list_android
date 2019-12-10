package com.example.to_do_list.persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.to_do_list.async.ListaDeleteTarefasAsync;
import com.example.to_do_list.async.ListaInsertAsync;
import com.example.to_do_list.async.ListaUpdateAsync;
import com.example.to_do_list.async.TarefaDeleteAsync;
import com.example.to_do_list.async.TarefaInsertAsync;
import com.example.to_do_list.async.TarefaUpdateAsync;
import com.example.to_do_list.models.Lista;
import com.example.to_do_list.models.Tarefa;

import java.util.List;

public class Repository {

    private ToDoListDatabase toDoListDatabase;

    public Repository(Context context) {
        toDoListDatabase = ToDoListDatabase.getInstance(context);
    }

    public void insertTarefa(Tarefa tarefa) {
        new TarefaInsertAsync(toDoListDatabase.getTarefaDao()).execute(tarefa);
    }

    public void updateTarefa (Tarefa tarefa){
        new TarefaUpdateAsync(toDoListDatabase.getTarefaDao()).execute(tarefa);
    }

    public LiveData<List<Tarefa>> retreviewTarefas(Lista lista){
        return toDoListDatabase.getTarefaDao().getTarefas(lista.getId());
    }

    public void delteTarefa(Tarefa tarefa) {
        new TarefaDeleteAsync(toDoListDatabase.getTarefaDao()).execute(tarefa) ;
    }

    public void deleteTarefaFromLista(Integer lista) {
        new ListaDeleteTarefasAsync(toDoListDatabase.getListaDao()).execute(lista) ;
    }
    public void insertLista(Lista lista) {
        new ListaInsertAsync(toDoListDatabase.getListaDao()).execute(lista);
    }

    public void updateLista (Lista lista){
        new ListaUpdateAsync(toDoListDatabase.getListaDao()).execute(lista);
    }

    public LiveData<List<Lista>> retreviewListas(){
        return toDoListDatabase.getListaDao().getListas();
    }

    public void deleteLista(Lista lista) {
        LiveData tarefas = toDoListDatabase.getTarefaDao().getTarefas(lista.getId());
        toDoListDatabase.getListaDao().delete(lista);
    }
}
