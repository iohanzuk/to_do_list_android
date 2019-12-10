package com.example.to_do_list.async;

import android.os.AsyncTask;

import com.example.to_do_list.persistence.ListaDao;

public class ListaDeleteTarefasAsync extends AsyncTask<Integer, Void, Void> {

    private ListaDao mListaDao;

    public ListaDeleteTarefasAsync(ListaDao mListaDao) {
        this.mListaDao = mListaDao;
    }

    @Override
    protected Void doInBackground(Integer... listas) {
        Integer id = listas[0];
        this.mListaDao.deleteTarefasFromLista(id);
        return null;
    }
}
