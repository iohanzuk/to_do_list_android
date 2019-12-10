package com.example.to_do_list.async;

import android.os.AsyncTask;

import com.example.to_do_list.models.Lista;
import com.example.to_do_list.persistence.ListaDao;

public class ListaUpdateAsync extends AsyncTask<Lista, Void, Void> {

    private ListaDao mListaDao;

    public ListaUpdateAsync(ListaDao mListaDao) {
        this.mListaDao = mListaDao;
    }

    @Override
    protected Void doInBackground(Lista... listas) {
        this.mListaDao.update(listas);
        return null;
    }
}
