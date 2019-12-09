package com.example.to_do_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.to_do_list.adpters.ListasRecyclerAdpter;
import com.example.to_do_list.adpters.TarefasRecyclerAdpter;
import com.example.to_do_list.models.Lista;
import com.example.to_do_list.models.Tarefa;
import com.example.to_do_list.util.VerticalSpacingItemDecorator;

import java.util.ArrayList;

public class TarefasActivity extends AppCompatActivity implements TarefasRecyclerAdpter.OnTarefasListener {

    private RecyclerView tarefasRecycleView;

    private ArrayList<Tarefa> tarefas = new ArrayList<>();
    private TarefasRecyclerAdpter tarefasRecyclerAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefas);
        tarefasRecycleView = findViewById(R.id.recycleViewTarefas);
        if(getIntent().hasExtra("lista")) {
            Lista lista = getIntent().getParcelableExtra("lista");
            initRecycleView();
            findTarefas(lista);
            setSupportActionBar((Toolbar) findViewById(R.id.lista_toolbar));
            setTitle(lista.getNome());
        }

    }

    private void initRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        tarefasRecycleView.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        tarefasRecycleView.addItemDecoration(itemDecorator);
        tarefasRecyclerAdpter = new TarefasRecyclerAdpter(tarefas, this);
        tarefasRecycleView.setAdapter(tarefasRecyclerAdpter );
    }

    private void findTarefas(Lista lista) {
        for(int i = 0; i < 1000 ; i++){
            tarefas.add(new Tarefa("Tarefa " + i, "Teste descri","Dec 2019"));
        }
        tarefasRecyclerAdpter.notifyDataSetChanged();
    }

    @Override
    public void onTarefaClick(int position) {
        Intent intent = new Intent(this, TarefaActivity.class);
        intent.putExtra("tarefa", tarefas.get(position));
        startActivity(intent);
    }
}
