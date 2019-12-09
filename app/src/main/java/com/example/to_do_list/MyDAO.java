package com.example.to_do_list;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.to_do_list.models.Lista;

import java.util.ArrayList;

@Dao
public interface MyDAO {

    @Insert
    public void inserir(Lista lista);

}
