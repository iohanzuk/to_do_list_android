package com.example.to_do_list;

import android.content.Intent;
import android.os.Bundle;

import com.example.to_do_list.adpters.ListasRecyclerAdpter;
import com.example.to_do_list.models.Lista;
import com.example.to_do_list.util.VerticalSpacingItemDecorator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class ListasActivity extends AppCompatActivity implements ListasRecyclerAdpter.OnListaListener {

    private RecyclerView mRecyclerView;

    private ArrayList<Lista> listas = new ArrayList<>();
    private ListasRecyclerAdpter listasRecyclerAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycleView);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        initRecycleView();
        insert();
        setSupportActionBar((Toolbar) findViewById(R.id.to_do_list_toolbar));
        setTitle("To Do List");
    }

    private void insert() {
        for(int i = 0; i < 1000 ; i++){
            listas.add(new Lista("Lista " + i, "Dec 2019"));
        }
        listasRecyclerAdpter.notifyDataSetChanged();
    }

    private void initRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerView.addItemDecoration(itemDecorator);
        listasRecyclerAdpter = new ListasRecyclerAdpter(listas, this);
        mRecyclerView.setAdapter(listasRecyclerAdpter);
    }

    @Override
    public void onListaClick(int position) {
        Intent intent = new Intent(this, TarefasActivity.class);
        intent.putExtra("lista", listas.get(position));
        startActivity(intent);
    }
}
