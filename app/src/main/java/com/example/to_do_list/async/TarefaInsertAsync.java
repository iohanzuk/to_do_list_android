package com.example.to_do_list.async;

import android.os.AsyncTask;

import com.example.to_do_list.models.Tarefa;
import com.example.to_do_list.persistence.TarefaDao;

public class TarefaInsertAsync extends AsyncTask<Tarefa, Void, Void> {

    private TarefaDao mTarefaDao;
    public TarefaInsertAsync(TarefaDao dao) {
        mTarefaDao = dao;
    }

    @Override
    protected Void doInBackground(Tarefa... tarefas) {
        mTarefaDao.insertTarefas(tarefas);
        return null;
    }
}
