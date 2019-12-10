package com.example.to_do_list.async;

import android.os.AsyncTask;

import com.example.to_do_list.models.Tarefa;
import com.example.to_do_list.persistence.TarefaDao;

public class TarefaUpdateAsync  extends AsyncTask<Tarefa, Void, Void> {

    private TarefaDao mTarefaDao;

    public TarefaUpdateAsync(TarefaDao dao) {
        mTarefaDao = dao;
    }

    @Override
    protected Void doInBackground(Tarefa... tarefas) {
        mTarefaDao.update(tarefas);
        return null;
    }
}