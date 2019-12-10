package com.example.to_do_list.async;

import android.os.AsyncTask;

import com.example.to_do_list.models.Lista;
import com.example.to_do_list.persistence.ListaDao;

public class ListaInsertAsync extends AsyncTask<Lista, Void, Void> {

    private ListaDao mListaDao;

    public ListaInsertAsync(ListaDao mListaDao) {
        this.mListaDao = mListaDao;
    }

    @Override
    protected Void doInBackground(Lista... listas) {
        this.mListaDao.insertListas(listas);
        return null;
    }
}
