package com.example.to_do_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.to_do_list.adpters.ListasRecyclerAdpter;
import com.example.to_do_list.adpters.TarefasRecyclerAdpter;
import com.example.to_do_list.models.Lista;
import com.example.to_do_list.models.Tarefa;
import com.example.to_do_list.util.VerticalSpacingItemDecorator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TarefasActivity extends AppCompatActivity implements
        TarefasRecyclerAdpter.OnTarefasListener,
        FloatingActionButton.OnClickListener {

    private RecyclerView tarefasRecycleView;

    private ArrayList<Tarefa> tarefas = new ArrayList<>();
    private TarefasRecyclerAdpter tarefasRecyclerAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefas);
        tarefasRecycleView = findViewById(R.id.recycleViewTarefas);
        findViewById(R.id.fab_tarefa).setOnClickListener(this);

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
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(tarefasRecycleView);
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

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, TarefaActivity.class);
        startActivity(intent);
    }

    private void delteTarefa(Tarefa tarefa) {
        tarefas.remove(tarefa);
        tarefasRecyclerAdpter.notifyDataSetChanged();
    }

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            delteTarefa(tarefas.get(viewHolder.getAdapterPosition()));
        }
    };
}
