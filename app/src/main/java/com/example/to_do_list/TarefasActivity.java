package com.example.to_do_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.to_do_list.adpters.TarefasRecyclerAdpter;
import com.example.to_do_list.models.Lista;
import com.example.to_do_list.models.Tarefa;
import com.example.to_do_list.persistence.Repository;
import com.example.to_do_list.util.VerticalSpacingItemDecorator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TarefasActivity extends AppCompatActivity implements
        TarefasRecyclerAdpter.OnTarefasListener,
        FloatingActionButton.OnClickListener {

    private RecyclerView tarefasRecycleView;

    private ArrayList<Tarefa> mTarefas = new ArrayList<>();
    private TarefasRecyclerAdpter tarefasRecyclerAdpter;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefas);
        tarefasRecycleView = findViewById(R.id.recycleViewTarefas);
        findViewById(R.id.fab_tarefa).setOnClickListener(this);
        repository = new Repository(this);

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
        tarefasRecyclerAdpter = new TarefasRecyclerAdpter(mTarefas, this);
        tarefasRecycleView.setAdapter(tarefasRecyclerAdpter );
    }

    private void findTarefas(Lista lista) {
        repository.retreviewTarefas(lista).observe(this, new Observer<List<Tarefa>>() {
            @Override
            public void onChanged(List<Tarefa> tarefas) {
                if (mTarefas.size() > 0 ) {
                    mTarefas.clear();
                }
                if (tarefas != null) {
                    mTarefas.addAll(tarefas);
                }
                tarefasRecyclerAdpter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onTarefaClick(int position) {
        Intent intent = new Intent(this, TarefaActivity.class);
        intent.putExtra("tarefa", mTarefas.get(position));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, TarefaActivity.class);
        startActivity(intent);
    }

    private void deleteTarefa(Tarefa tarefa) {
        mTarefas.remove(tarefa);
        tarefasRecyclerAdpter.notifyDataSetChanged();
        repository.delteTarefa(tarefa);
    }

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteTarefa(mTarefas.get(viewHolder.getAdapterPosition()));
        }
    };
}
